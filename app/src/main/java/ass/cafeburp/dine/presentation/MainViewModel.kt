package ass.cafeburp.dine.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import ass.cafeburp.dine.data.local.daos.CartDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val cartDao: CartDao) : ViewModel() {

    val cartCount: LiveData<Int> =
        flow { emitAll(cartDao.getCartCount()) }.asLiveData(viewModelScope.coroutineContext + Default)

}