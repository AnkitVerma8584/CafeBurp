package ass.cafeburp.dine.presentation.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ass.cafeburp.dine.R
import ass.cafeburp.dine.data.local.daos.CartDao
import ass.cafeburp.dine.data.local.mapper.productToCartItem
import ass.cafeburp.dine.data.remote.Api
import ass.cafeburp.dine.domain.util.Resource
import ass.cafeburp.dine.domain.util.StringUtil
import ass.cafeburp.dine.domain.modals.category.Category
import ass.cafeburp.dine.domain.modals.product.Product
import ass.cafeburp.dine.domain.repositories.HomeRepository
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
    private val homeRepository: HomeRepository,
    private val cartDao: CartDao
) : ViewModel() {

    var categoryName: StringUtil = StringUtil.StringResource(R.string.food)
    private var categoryId: Int = 0
    private var page: Int = 1
    val isLastPage = MutableStateFlow(false)

    val isLoading = MutableStateFlow(false)

    fun setCategory(category: Category) {
        if (categoryId == category.id)
            return
        categoryName = StringUtil.DynamicText(category.category_name)
        categoryId = category.id
        page = 1
        isLastPage.value = false
        foodResponse = null
        getProducts()
    }

    private val _categories: MutableLiveData<Resource<List<Category>>> =
        MutableLiveData<Resource<List<Category>>>()
    val categories: LiveData<Resource<List<Category>>> = _categories

    private val _products: MutableLiveData<List<Product>> = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _productsError: Channel<StringUtil> = Channel()
    val productsError: Flow<StringUtil> = _productsError.receiveAsFlow()

    init {
        viewModelScope.launch(Default) {
            _categories.postValue(homeRepository.getCategories())
        }
        getProducts()
    }

    private var foodResponse: Resource<MutableList<Product>>? = null

    fun getProducts() {
        viewModelScope.launch(Default) {
            when (val result = homeRepository.getProducts(categoryId, page)) {
                is Resource.Success -> {
                    val newProducts: MutableList<Product> = result.data!!
                    isLastPage.value = newProducts.size < Api.FOOD_LIMIT
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

    fun addItem(product: Product) {
        viewModelScope.launch(Default) {
            cartDao.addToCart(product.productToCartItem())
        }
    }

    suspend fun checkInCart(id: Int): Boolean = cartDao.isItemExists(id)
}