package ass.cafeburp.dine.presentation.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ass.cafeburp.dine.domain.implementations.HomeRepositoryImpl
import ass.cafeburp.dine.domain.modals.Category
import ass.cafeburp.dine.domain.modals.Product
import ass.cafeburp.dine.util.Resource
import ass.cafeburp.dine.util.states.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepositoryImpl: HomeRepositoryImpl) :
    ViewModel() {

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Idle)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private val _categories: MutableLiveData<Resource<List<Category>>> =
        MutableLiveData<Resource<List<Category>>>()
    val categories: LiveData<Resource<List<Category>>> = _categories

    private val _products: MutableLiveData<Resource<List<Product>>> =
        MutableLiveData<Resource<List<Product>>>()
    val products: LiveData<Resource<List<Product>>> = _products

    init {
        viewModelScope.launch(Dispatchers.Default) {
            _categories.postValue(homeRepositoryImpl.getCategories())
            _products.postValue(homeRepositoryImpl.getProducts())
        }
    }
}