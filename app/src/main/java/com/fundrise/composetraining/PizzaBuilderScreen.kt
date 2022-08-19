package com.fundrise.composetraining

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun PizzaBuilderScreen(
    modifier: Modifier = Modifier
) {
    var pizza by rememberSaveable { mutableStateOf(Pizza()) }

    Box {
        Column(modifier = modifier) {
            PizzaSizeDropdown(
                pizza = pizza,
                onEditPizza = { pizza = it },
                modifier = modifier,
            )
            ToppingsList(
                pizza = pizza,
                onEditPizza = { pizza = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = true)
            )
        }
        OrderButton(
            pizza = pizza,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.horizontal))
                .align(Alignment.BottomCenter)
        )
    }

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