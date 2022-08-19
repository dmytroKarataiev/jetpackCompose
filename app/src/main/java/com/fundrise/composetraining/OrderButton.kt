package com.fundrise.composetraining

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import java.text.NumberFormat

@Composable
fun OrderButton(
    pizza: Pizza,
    modifier: Modifier,
) {
    Button(
        modifier = modifier,
        onClick = { /*TODO*/ }
    ) {
        val currencyFormatter = remember {
            NumberFormat.getCurrencyInstance()
        }

        val price = currencyFormatter.format(pizza.price)
        Text(text = stringResource(id = R.string.place_order_button, price).uppercase())
    }
}