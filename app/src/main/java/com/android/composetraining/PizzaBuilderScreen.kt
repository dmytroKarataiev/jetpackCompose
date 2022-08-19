package com.android.composetraining

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PizzaBuilderScreen(
    modifier: Modifier = Modifier,
    repo: OrderingRepository = LocalRepoManager.current
) {
    var pizza by rememberSaveable { mutableStateOf(Pizza()) }

    var toppings by remember { mutableStateOf(listOf<Topping>()) }
    LaunchedEffect(Unit) {
        toppings = repo.getToppings()
    }

    val initialPrice = stringResource(R.string.unknown_price)
    val formattedPrice by produceState(initialPrice, pizza) {
        value = repo.calculateFormattedPrice(pizza)
    }

    val scope = rememberCoroutineScope()
    var currentJob by remember { mutableStateOf<Job?>(null) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) }
            )
        },
        content = { padding ->
            Box {
                Column(modifier = modifier.padding(padding)) {
                    PizzaSizeDropdown(
                        pizza = pizza,
                        onEditPizza = { pizza = it },
                        modifier = modifier,
                    )
                    ToppingsList(
                        toppings = toppings,
                        pizza = pizza,
                        onEditPizza = { pizza = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = true)
                    )
                }

                if (currentJob != null) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.75f))
                            .fillMaxSize(),
                    ) {
                        Text(
                            stringResource(R.string.ordering_pizza),
                            style = MaterialTheme.typography.h4
                        )
                        Button(
                            onClick = {
                                currentJob?.cancel()
                                currentJob = null
                            }
                        ) {
                            Text(stringResource(R.string.cancel))
                        }
                    }
                }

                var editable by remember { mutableStateOf(true) }
                AnimatedVisibility(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    visible = editable,
                    exit = scaleOut()
                ) {
                    OrderButton(
                        formattedPrice = formattedPrice,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.horizontal))
                    ) {
                        editable = false
                        currentJob = scope.launch {
                            repo.placeOrder(pizza)
                            currentJob = null
                        }
                    }
                }

            }
        }
    )
}

@Composable
private fun PizzaSizeDropdown(
    pizza: Pizza,
    onEditPizza: (Pizza) -> Unit,
    modifier: Modifier,
) {
    Box {
        var expanded by remember { mutableStateOf(false) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .clickable(onClick = { expanded = true })
                .padding(
                    vertical = dimensionResource(id = R.dimen.vertical),
                    horizontal = dimensionResource(id = R.dimen.horizontal)
                )
        ) {
            Text(
                stringResource(id = pizza.size.textName),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = true)
                    .padding(dimensionResource(id = R.dimen.horizontal))
            )
            if (expanded) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_drop_up_24),
                    contentDescription = "expand dropwdown"
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24),
                    contentDescription = "expand dropwdown"
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            PizzaSize.values().forEach {
                DropdownMenuItem(onClick = {
                    onEditPizza(pizza.withSize(it))
                    expanded = false
                }) {
                    Text(text = stringResource(id = it.textName))
                }

            }
        }
    }
}