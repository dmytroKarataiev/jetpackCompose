package com.android.composetraining

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import java.util.UUID

class OrderingRepository(
    private val currencyFormatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US),
    private val coroutineScope: CoroutineScope = GlobalScope
) {
    private val orderStatusMap: MutableMap<UUID, StateFlow<OrderStatus>> = mutableMapOf()

    suspend fun getToppings(): List<Topping> {
        delay(1000)
        return Topping.values().toList()
    }

    suspend fun getToppingPlacementSuggestion(): ToppingPlacement {
        return try {
            val potentialOptions = listOf(
                ToppingPlacement.All,
                ToppingPlacement.Left,
                ToppingPlacement.Right
            )
            delay(5000)
            potentialOptions.random()
        } catch (ex: CancellationException) {
            Log.d("OrderingRepository", "Request to get suggestion cancelled.")
            throw ex
        }
    }

    suspend fun calculateFormattedPrice(pizza: Pizza): String {
        delay(3000)
        return currencyFormatter.format(pizza.price)
    }

    suspend fun placeOrder(pizza: Pizza): UUID {
        delay(5000)

        val orderId = UUID.randomUUID()
        processOrder(orderId, pizza)

        return orderId
    }

    fun getOrderStatus(pizzaId: UUID): Flow<OrderStatus> {
        if (pizzaId == DemoPizzaId) {
            processOrder(DemoPizzaId, Pizza())
        }

        return orderStatusMap[pizzaId] ?: throw IllegalArgumentException("Invalid ID")
    }

    private fun processOrder(pizzaId: UUID, pizza: Pizza) {
        val orderFlow = MutableStateFlow(OrderStatus.NotStarted)

        coroutineScope.launch {
            delay(2000)
            orderFlow.update { OrderStatus.Accepted }
            delay(2000)
            orderFlow.update { OrderStatus.BeingPrepared }

            val cookingTime = pizza.toppings.keys.size * 2500L + 1000L
            delay(cookingTime)
            orderFlow.update { OrderStatus.BeingDelivered }

            delay(3000)
            orderFlow.update { OrderStatus.Delivered }
        }

        orderStatusMap[pizzaId] = orderFlow.asStateFlow()
    }

    companion object {
        val DemoPizzaId: UUID = UUID.fromString("047fbae5-08c8-42bf-a7c7-143a1b66cdf0")
    }
}

enum class OrderStatus(@StringRes val stringResource: Int) {
    NotStarted(R.string.status_not_started),
    Accepted(R.string.status_accepted),
    BeingPrepared(R.string.status_being_prepared),
    BeingDelivered(R.string.status_being_delivered),
    Delivered(R.string.status_delivered)
}

val LocalOrderingRepository = compositionLocalOf { OrderingRepository() }