package ass.cafeburp.dine.presentation.dialogs.product_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ass.cafeburp.dine.data.local.daos.CartDao
import ass.cafeburp.dine.data.local.mapper.productToCartItem
import ass.cafeburp.dine.domain.modals.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductItemInfoViewModel @Inject constructor(private val cartDao: CartDao) : ViewModel() {

    suspend fun checkInCart(id: Int): Boolean = cartDao.isItemExists(id)

    fun addItem(product: Product) {
        viewModelScope.launch(Dispatchers.Default) {
            cartDao.addToCart(product.productToCartItem())
        }
    }

}