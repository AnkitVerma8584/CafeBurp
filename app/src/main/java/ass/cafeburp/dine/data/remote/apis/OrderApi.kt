package ass.cafeburp.dine.data.remote.apis

import ass.cafeburp.dine.data.remote.Api
import ass.cafeburp.dine.data.remote.helpers.RetrofitResult
import ass.cafeburp.dine.domain.modals.Order
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApi {
    @POST(Api.POST_ORDER)
    suspend fun placeOrder(
        @Body order: Order
    ): RetrofitResult<String>
}