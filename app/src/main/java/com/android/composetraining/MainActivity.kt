package com.android.composetraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.livedata.observeAsState
import com.android.composetraining.ui.composables.ErrorView
import com.android.composetraining.ui.composables.Loading
import com.android.composetraining.ui.composables.PizzaBuilderScreen
import com.android.composetraining.ui.composables.PizzaTrackerScreen
import com.android.composetraining.viewmodels.PizzaViewModel
import com.android.composetraining.viewstates.ViewState

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
                    ViewState.Loading -> Loading {
                        viewModel.getToppings()
                    }
                    ViewState.Error -> ErrorView {
                        viewModel.getToppings()
                    }
                    is ViewState.Ordered -> PizzaTrackerScreen(
                        orderId = viewState.id,
                        orderStatus = viewState.status
                    )
                }

            }
        }
    }

}