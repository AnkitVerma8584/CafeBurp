package ass.cafeburp.dine.domain.implementations

import ass.cafeburp.dine.R
import ass.cafeburp.dine.data.remote.apis.HomeApi
import ass.cafeburp.dine.data.remote.helpers.Resource
import ass.cafeburp.dine.data.remote.helpers.StringUtil
import ass.cafeburp.dine.data.remote.repositories.HomeRepository
import ass.cafeburp.dine.domain.modals.Category
import ass.cafeburp.dine.domain.modals.Product
import ass.cafeburp.dine.util.CheckInternet
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi,
    private val checkInternet: CheckInternet
) : HomeRepository {
    override suspend fun getCategories(): Resource<List<Category>> {
        return try {
            if (checkInternet.hasNoInternetConnection())
                Resource.Error<List<Category>>(message = StringUtil.StringResource(R.string.no_internet))
            val data = homeApi.getCategories()
            if (data.success)
                Resource.Success(data = data.data)
            else Resource.Error(message = StringUtil.DynamicText(data.message))
        } catch (e: Exception) {
            Resource.Error(
                e.localizedMessage?.let {
                    StringUtil.DynamicText(it)
                } ?: StringUtil.StringResource(R.string.some_error)
            )
        }
    }

    override suspend fun getProducts(): Resource<List<Product>> {
        return try {
            if (checkInternet.hasNoInternetConnection())
                Resource.Error<List<Product>>(message = StringUtil.StringResource(R.string.no_internet))
            val data = homeApi.getProducts()
            if (data.success)
                Resource.Success(data = data.data)
            else Resource.Error(message = StringUtil.DynamicText(data.message))
        } catch (e: Exception) {
            Resource.Error(
                e.localizedMessage?.let {
                    StringUtil.DynamicText(it)
                } ?: StringUtil.StringResource(R.string.some_error)
            )
        }
    }
}