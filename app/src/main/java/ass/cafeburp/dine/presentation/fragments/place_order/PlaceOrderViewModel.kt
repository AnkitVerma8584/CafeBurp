package ass.cafeburp.dine.presentation.fragments.place_order

import androidx.lifecycle.*
import ass.cafeburp.dine.data.local.daos.CartDao
import ass.cafeburp.dine.data.local.mapper.mapItem
import ass.cafeburp.dine.domain.util.Resource
import ass.cafeburp.dine.domain.modals.order.Order
import ass.cafeburp.dine.domain.repositories.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlaceOrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val savedStateHandle: SavedStateHandle,
    private val cartDao: CartDao
) : ViewModel() {

    private val _orderState: MutableLiveData<Resource<String>> = MutableLiveData()
    val orderState: LiveData<Resource<String>> = _orderState

    init {
        viewModelScope.launch(Default) {
            val order = Order(
                savedStateHandle.get<String>("name") ?: "",
                savedStateHandle.get<String>("mobile") ?: "",
                savedStateHandle.get<String>("table") ?: "",
                cartDao.getTotal(),
                cartDao.getItemsForOrder().map { it.mapItem() }
            )
            val result = orderRepository.placeOrder(order)
            _orderState.postValue(result)
        }
    }

    suspend fun emptyCart() = withContext(Default) {
        cartDao.emptyCart()
    }
}