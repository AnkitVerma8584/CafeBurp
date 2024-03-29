package ass.cafeburp.dine.data.remote.implementations

import ass.cafeburp.dine.R
import ass.cafeburp.dine.data.remote.apis.OrderApi
import ass.cafeburp.dine.domain.modals.order.Order
import ass.cafeburp.dine.domain.repositories.OrderRepository
import ass.cafeburp.dine.domain.util.Resource
import ass.cafeburp.dine.domain.util.StringUtil
import ass.cafeburp.dine.util.CheckInternet
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderApi: OrderApi, private val checkInternet: CheckInternet
) : OrderRepository {
    override suspend fun placeOrder(order: Order): Resource<String> {
        return if (checkInternet.hasNoInternetConnection())
            Resource.Error(message = StringUtil.StringResource(R.string.no_internet))
        else try {
            val data = orderApi.placeOrder(order)
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