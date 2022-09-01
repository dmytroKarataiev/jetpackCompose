package com.android.composetraining.viewstates

import com.android.composetraining.OrderStatus
import com.android.composetraining.data.Pizza
import com.android.composetraining.data.Topping
import java.util.UUID

sealed class ViewState {

    object Error : ViewState()

    object Loading : ViewState()

    data class Content(
        val toppings: List<Topping>,
        val price: String,
        val pizza: Pizza,
    ) : ViewState()

    data class Ordered(
        val id: UUID,
        val status: OrderStatus
    ) : ViewState()

}