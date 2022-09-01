package com.android.composetraining.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.android.composetraining.R
import com.android.composetraining.data.Pizza
import com.android.composetraining.data.Topping

@Composable
fun ToppingsList(
    pizza: Pizza,
    onEditPizza: (Pizza) -> Unit,
    modifier: Modifier,
    toppings: List<Topping>,
) {
    var toppingBeingAdded by rememberSaveable { mutableStateOf<Topping?>(null) }

    toppingBeingAdded?.let { topping ->
        ToppingPlacementDialog(
            topping = topping,
            onSetToppingPlacement = { placement ->
                onEditPizza(pizza.withTopping(topping, placement))
            },
            onDismissRequest = {
                toppingBeingAdded = null
            }
        )
    }

    LazyColumn(modifier = modifier) {
        item {
            PizzaHeroImage(
                pizza = pizza,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.horizontal))
            )
        }

        items(items = toppings) { topping ->
            ToppingCell(
                topping = topping,
                placement = pizza.toppings[topping],
                modifier = modifier,
                onClickTopping = {
                    toppingBeingAdded = topping
                }
            )
        }

        item {
            Spacer(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.verticalSpacer))
            )
        }
    }
}