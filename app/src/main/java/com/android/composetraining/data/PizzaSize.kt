package com.android.composetraining.data

import androidx.annotation.StringRes
import com.android.composetraining.R

enum class PizzaSize(
    val multiplier: Float,
    @StringRes val textName: Int
) {
    SMALL(0.8f, R.string.small),
    MEDIUM(1.0f, R.string.medium),
    LARGE(1.2f, R.string.large),
    EXTRA_LARGE(1.5f, R.string.extra_large)
}
