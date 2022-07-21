package ass.cafeburp.dine.data.remote.apis

import ass.cafeburp.dine.data.remote.Api
import ass.cafeburp.dine.data.remote.helpers.RetrofitResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface OrderApi {
    @POST(Api.POST_ORDER)
    @FormUrlEncoded
    suspend fun placeOrder(
        @Field("mobile") mobile: String,
        @Field("table") table: String
    ): RetrofitResult<String>
}