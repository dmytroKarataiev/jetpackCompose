package com.android.composetraining.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.android.composetraining.R
import com.android.composetraining.data.Pizza
import com.android.composetraining.data.Topping

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PizzaBuilderScreen(
    modifier: Modifier = Modifier,
    toppings: List<Topping>,
    formattedPrice: String,
    pizza: Pizza,
    editPizza: (Pizza) -> Unit,
    placeOrder: (Pizza) -> Unit,
) {

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
                        onEditPizza = editPizza,
                        modifier = modifier,
                    )
                    ToppingsList(
                        toppings = toppings,
                        pizza = pizza,
                        onEditPizza = editPizza,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = true)
                    )
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
                        placeOrder(pizza)
                    }
                }

            }
        }
    )
}