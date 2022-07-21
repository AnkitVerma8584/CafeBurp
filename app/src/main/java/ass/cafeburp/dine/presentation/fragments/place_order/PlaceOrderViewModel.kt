package ass.cafeburp.dine.presentation.fragments.place_order

import androidx.lifecycle.*
import ass.cafeburp.dine.data.fake_data.FakeOrderRepo
import ass.cafeburp.dine.data.local.daos.CartDao
import ass.cafeburp.dine.data.remote.helpers.Resource
import ass.cafeburp.dine.domain.implementations.OrderRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlaceOrderViewModel @Inject constructor(
    private val orderRepositoryImpl: OrderRepositoryImpl,
    private val fakeOrderRepo: FakeOrderRepo,
    private val savedStateHandle: SavedStateHandle,
    private val cartDao: CartDao
) : ViewModel() {

    private val _orderState: MutableLiveData<Resource<String>> = MutableLiveData()
    val orderState: LiveData<Resource<String>> = _orderState

    init {
        viewModelScope.launch(Default) {
            _orderState.postValue(
                fakeOrderRepo.placeOrder(
                    savedStateHandle.get<String>("mobile") ?: "",
                    savedStateHandle.get<String>("table") ?: ""
                )
            )
        }
    }

    suspend fun emptyCart() =
        withContext(Default) {
            cartDao.emptyCart()
        }
}