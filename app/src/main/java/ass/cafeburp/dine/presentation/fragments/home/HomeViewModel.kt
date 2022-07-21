package ass.cafeburp.dine.presentation.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ass.cafeburp.dine.data.fake_data.FakeHomeRepo
import ass.cafeburp.dine.data.remote.helpers.Resource
import ass.cafeburp.dine.domain.implementations.HomeRepositoryImpl
import ass.cafeburp.dine.domain.modals.Category
import ass.cafeburp.dine.domain.modals.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepositoryImpl: HomeRepositoryImpl,
    private val fakeHomeRepo: FakeHomeRepo
) : ViewModel() {

    private val _categories: MutableLiveData<Resource<List<Category>>> =
        MutableLiveData<Resource<List<Category>>>()
    val categories: LiveData<Resource<List<Category>>> = _categories

    private val _products: MutableLiveData<Resource<List<Product>>> =
        MutableLiveData<Resource<List<Product>>>()
    val products: LiveData<Resource<List<Product>>> = _products

    init {
        viewModelScope.launch(Dispatchers.Default) {
            launch { _categories.postValue(fakeHomeRepo.getCategories()) }
            launch { _products.postValue(fakeHomeRepo.getProducts()) }
        }
    }

    fun getCategoryProducts(id: Int) {

    }

}