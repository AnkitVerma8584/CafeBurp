package ass.cafeburp.dine.data.remote.apis

import ass.cafeburp.dine.data.remote.Api
import ass.cafeburp.dine.domain.modals.category.Category
import ass.cafeburp.dine.domain.modals.product.Product
import ass.cafeburp.dine.domain.util.RetrofitResult
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {
    @GET(Api.GET_CATEGORIES)
    suspend fun getCategories(): RetrofitResult<List<Category>>

    @GET(Api.GET_FOOD)
    suspend fun getProducts(
        @Query("cat_id") categoryId: Int = 0,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = Api.FOOD_LIMIT
    ): RetrofitResult<MutableList<Product>>
}