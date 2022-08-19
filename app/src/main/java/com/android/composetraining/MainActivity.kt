package com.android.composetraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

/**
 * Main entrance point to the app.
 */
class MainActivity : ComponentActivity() {

    private lateinit var repo: OrderingRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repo = OrderingRepository()
        LocalRepoManager = compositionLocalOf { repo }
        setContent {
            AppTheme {
                // PizzaBuilderScreen()
                PizzaTrackerScreen(OrderingRepository.DemoPizzaId)
            }
        }
    }

}

lateinit var LocalRepoManager : ProvidableCompositionLocal<OrderingRepository>