package ass.cafeburp.dine.data.remote.apis

import ass.cafeburp.dine.data.remote.Api
import ass.cafeburp.dine.domain.modals.Category
import ass.cafeburp.dine.domain.modals.Product
import ass.cafeburp.dine.data.remote.helpers.RetrofitResult
import retrofit2.http.GET

interface HomeApi {
    @GET(Api.GET_CATEGORIES)
    suspend fun getCategories(): RetrofitResult<List<Category>>

    @GET(Api.GET_FOOD)
    suspend fun getProducts(): RetrofitResult<List<Product>>
}