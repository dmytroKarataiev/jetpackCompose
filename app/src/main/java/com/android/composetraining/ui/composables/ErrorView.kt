package com.android.composetraining.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.android.composetraining.R

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
) {
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
            onClick = onRefresh
        ) {
            Text(stringResource(R.string.cancel))
        }
    }
}