package com.android.composetraining

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PizzaViewModel(
    private val repo: OrderingRepository
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>(ViewState.Loading)
    val state: LiveData<ViewState> = _state

    private var savedToppings = emptyList<Topping>()
    private var savedPizza = Pizza(toppings = mapOf(), size = PizzaSize.MEDIUM)

    init {
        getToppings()
    }

    fun getToppings() {
        viewModelScope.launch(Dispatchers.IO) {
            savedToppings = repo.getToppings()
            _state.postValue(
                ViewState.Content(
                    toppings = savedToppings,
                    price = "",
                    pizza = savedPizza
                )
            )
        }
    }

    fun getPrice(pizza: Pizza) {
        viewModelScope.launch(Dispatchers.IO) {
            val price = repo.calculateFormattedPrice(pizza)
            _state.postValue(ViewState.Content(savedToppings, price, savedPizza))
        }
    }

    fun placeOrder(pizza: Pizza) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = repo.placeOrder(pizza)
            repo.getOrderStatus(id).collectLatest {
                _state.postValue(ViewState.Ordered(id, it))
            }
        }
    }

    companion object {

        @Suppress("UNCHECKED_CAST")
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PizzaViewModel(OrderingRepository()) as T
            }
        }

    }

}