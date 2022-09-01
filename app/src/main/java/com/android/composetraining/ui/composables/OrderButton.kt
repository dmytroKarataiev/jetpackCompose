package com.android.composetraining.ui.composables

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.android.composetraining.R

@Composable
fun OrderButton(
    formattedPrice: String,
    modifier: Modifier,
    onButtonClicked: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onButtonClicked
    ) {
        Text(text = stringResource(id = R.string.place_order_button, formattedPrice).uppercase())
    }
}