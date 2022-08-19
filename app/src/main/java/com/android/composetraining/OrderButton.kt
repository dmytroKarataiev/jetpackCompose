package com.android.composetraining

import android.widget.Toast
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import java.text.NumberFormat

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