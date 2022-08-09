package ass.cafeburp.dine.data.remote.implementations

import ass.cafeburp.dine.R
import ass.cafeburp.dine.data.remote.apis.HomeApi
import ass.cafeburp.dine.domain.modals.category.Category
import ass.cafeburp.dine.domain.modals.product.Product
import ass.cafeburp.dine.domain.repositories.HomeRepository
import ass.cafeburp.dine.domain.util.Resource
import ass.cafeburp.dine.domain.util.StringUtil
import ass.cafeburp.dine.util.CheckInternet
import ass.cafeburp.dine.util.printLog
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi,
    private val checkInternet: CheckInternet
) : HomeRepository {

    override suspend fun getCategories(): Resource<List<Category>> {
        return if (checkInternet.hasNoInternetConnection())
            Resource.Error(message = StringUtil.StringResource(R.string.no_internet))
        else try {
            val data = homeApi.getCategories()
            data.printLog("HOME_CATEGORY")
            if (data.success)
                Resource.Success(data = data.data)
            else Resource.Error(message = StringUtil.DynamicText(data.message))
        } catch (e: Exception) {
            Resource.Error(
                message = e.localizedMessage?.let {
                    StringUtil.DynamicText(it)
                } ?: StringUtil.StringResource(R.string.some_error)
            )
        }
    }

    override suspend fun getProducts(categoryId: Int, page: Int): Resource<MutableList<Product>> {
        return if (checkInternet.hasNoInternetConnection())
            Resource.Error(message = StringUtil.StringResource(R.string.no_internet))
        else
            try {
                val data = homeApi.getProducts(categoryId, page)
                data.printLog("HOME_PRODUCT")
                if (data.success)
                    Resource.Success(data = data.data)
                else Resource.Error(message = StringUtil.DynamicText(data.message))
            } catch (e: Exception) {
                Resource.Error(
                    message = e.localizedMessage?.let {
                        StringUtil.DynamicText(it)
                    } ?: StringUtil.StringResource(R.string.some_error)
                )
            }
    }
}