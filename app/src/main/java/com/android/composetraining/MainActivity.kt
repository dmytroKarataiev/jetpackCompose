package com.android.composetraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.livedata.observeAsState

/**
 * Main entrance point to the app.
 */
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<PizzaViewModel> {
        PizzaViewModel.factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val state = viewModel.state.observeAsState(ViewState.Loading)
                when (val viewState = state.value) {
                    is ViewState.Content -> PizzaBuilderScreen(
                        toppings = viewState.toppings,
                        formattedPrice = viewState.price,
                        placeOrder = { viewModel.placeOrder(it) },
                        editPizza = { viewModel.getPrice(it) },
                        pizza = viewState.pizza,
                    )
                    ViewState.Loading -> Loading(onCancel = {})
                    ViewState.Error -> TODO()
                    is ViewState.Ordered -> PizzaTrackerScreen(
                        orderId = viewState.id,
                        orderStatus = viewState.status
                    )
                }

            }
        }
    }

}