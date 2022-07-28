package ass.cafeburp.dine.presentation.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ass.cafeburp.dine.R
import ass.cafeburp.dine.data.remote.Api
import ass.cafeburp.dine.data.remote.helpers.Resource
import ass.cafeburp.dine.data.remote.helpers.StringUtil
import ass.cafeburp.dine.domain.implementations.HomeRepositoryImpl
import ass.cafeburp.dine.domain.modals.Category
import ass.cafeburp.dine.domain.modals.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepositoryImpl: HomeRepositoryImpl
) : ViewModel() {

    var categoryName: StringUtil = StringUtil.StringResource(R.string.food)
    private var categoryId: Int = 0
    private var page: Int = 1
    var isLastPage = false

    val isLoading = MutableStateFlow(false)

    fun setCategory(category: Category) {
        if (categoryId == category.id)
            return
        categoryName = StringUtil.DynamicText(category.category_name)
        categoryId = category.id
        page = 1
        isLastPage = false
        foodResponse = null
        getProducts()
    }

    private val _categories: MutableLiveData<Resource<List<Category>>> =
        MutableLiveData<Resource<List<Category>>>()
    val categories: LiveData<Resource<List<Category>>> = _categories

    private val _products: MutableLiveData<List<Product>> =
        MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _productsError: Channel<StringUtil> = Channel()
    val productsError: Flow<StringUtil> = _productsError.receiveAsFlow()

    init {
        viewModelScope.launch(Default) {
            _categories.postValue(homeRepositoryImpl.getCategories())
        }
        getProducts()
    }

    private var foodResponse: Resource<MutableList<Product>>? = null

    fun getProducts() {
        viewModelScope.launch(Default) {
            when (val result = homeRepositoryImpl.getProducts(categoryId, page)) {
                is Resource.Success -> {
                    val newProducts: MutableList<Product> = result.data!!
                    isLastPage = newProducts.size < Api.FOOD_LIMIT
                    page++
                    if (foodResponse == null) {
                        foodResponse = result
                    } else
                        foodResponse?.data?.addAll(newProducts)

                    _products.postValue(foodResponse?.data?.toList())
                }
                is Resource.Error -> {
                    if (foodResponse == null) {
                        _products.postValue(emptyList())
                    }
                    withContext(Main.immediate) {
                        _productsError.send(result.message!!)
                    }
                }
            }
            isLoading.value = false
        }
    }
}