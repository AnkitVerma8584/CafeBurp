package ass.cafeburp.dine.presentation.fragments.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ass.cafeburp.dine.data.local.daos.CartDao
import ass.cafeburp.dine.data.local.modals.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartDao: CartDao) : ViewModel() {

    private val _price: MutableStateFlow<Double> = MutableStateFlow(0.0)
    val price = _price.asStateFlow()

    fun setPrice(list: List<CartItem>) {
        var currentPrice = 0.0
        list.forEach {
            currentPrice += it.totalPrice
        }
        _price.value = currentPrice
    }

    fun decreaseItem(cartItem: CartItem) {
        viewModelScope.launch(Default) {
            if (cartItem.quantity > 1)
                cartDao.decreaseItem(cartItem.id)
            else cartDao.removeFromCart(cartItem)
        }
    }

    fun increaseItem(cartItem: CartItem) {
        viewModelScope.launch(Default) {
            cartDao.increaseItem(cartItem.id)
        }
    }
}