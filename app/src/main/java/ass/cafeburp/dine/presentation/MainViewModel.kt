package ass.cafeburp.dine.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import ass.cafeburp.dine.data.local.daos.CartDao
import ass.cafeburp.dine.data.local.mapper.productToCartItem
import ass.cafeburp.dine.data.local.modals.CartItem
import ass.cafeburp.dine.domain.modals.Product
import ass.cafeburp.dine.util.printLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val cartDao: CartDao) : ViewModel() {

    val cartItems: LiveData<List<CartItem>> =
        flow { emitAll(cartDao.getAllCartItems()) }.asLiveData(viewModelScope.coroutineContext + Default)

    fun addItem(product: Product) {
        viewModelScope.launch(Default) {
            "Adding $product".printLog()
            cartDao.addToCart(product.productToCartItem())
        }
    }

    suspend fun checkInCart(id: Int): Boolean = cartDao.isItemExists(id)

}