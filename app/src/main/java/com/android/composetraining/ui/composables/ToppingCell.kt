package com.android.composetraining.ui.composables

import android.os.Parcelable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.android.composetraining.R
import com.android.composetraining.data.Topping
import com.android.composetraining.data.ToppingPlacement
import kotlinx.parcelize.Parcelize

@Composable
fun ToppingCell(
    topping: Topping,
    placement: ToppingPlacement?,
    modifier: Modifier,
    onClickTopping: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onClickTopping() }
            .padding(
                vertical = dimensionResource(id = R.dimen.vertical),
                horizontal = dimensionResource(id = R.dimen.horizontal)
            )
    ) {

        Checkbox(
            checked = placement != null,
            onCheckedChange = { onClickTopping() }
        )
        Column(
            modifier =
            Modifier
                .weight(1f, fill = true)
                .padding(dimensionResource(id = R.dimen.minimal))
        ) {
            Text(
                text = stringResource(id = topping.toppingName),
                style = MaterialTheme.typography.body1
            )
            placement?.let {
                Text(
                    text = stringResource(id = it.label),
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Preview
@Composable
fun ToppingCellPreview() {
    ToppingCell(
        topping = Topping.Basil,
        placement = ToppingPlacement.Left,
        modifier = Modifier,
        onClickTopping = {}
    )
}

@Preview
@Composable
fun ToppingCellPreviewNotOnPizza() {
    ToppingCell(
        topping = Topping.Peppers,
        placement = null,
        modifier = Modifier,
        onClickTopping = {}
    )
}

@Parcelize
data class TestClass(val checked: Boolean) : Parcelable