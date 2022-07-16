package ass.cafeburp.dine.data.remote.repositories

import ass.cafeburp.dine.domain.modals.Category
import ass.cafeburp.dine.domain.modals.Product
import ass.cafeburp.dine.util.Resource

interface HomeRepository {

    suspend fun getCategories(): Resource<List<Category>>

    suspend fun getProducts(): Resource<List<Product>>

}