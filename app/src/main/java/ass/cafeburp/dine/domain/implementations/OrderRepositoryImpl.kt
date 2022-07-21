package ass.cafeburp.dine.domain.implementations

import ass.cafeburp.dine.R
import ass.cafeburp.dine.data.remote.apis.OrderApi
import ass.cafeburp.dine.data.remote.helpers.Resource
import ass.cafeburp.dine.data.remote.helpers.StringUtil
import ass.cafeburp.dine.data.remote.repositories.OrderRepository
import ass.cafeburp.dine.util.CheckInternet
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderApi: OrderApi, private val checkInternet: CheckInternet
) : OrderRepository {
    override suspend fun placeOrder(mobile: String, table: String): Resource<String> {

        return try {
            if (checkInternet.hasNoInternetConnection())
                Resource.Error<String>(message = StringUtil.StringResource(R.string.no_internet))
            val data = orderApi.placeOrder(mobile, table)
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