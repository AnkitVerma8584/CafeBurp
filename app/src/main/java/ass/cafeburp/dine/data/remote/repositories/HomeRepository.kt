package ass.cafeburp.dine.data.remote.repositories

import ass.cafeburp.dine.data.remote.helpers.Resource
import ass.cafeburp.dine.domain.modals.Category
import ass.cafeburp.dine.domain.modals.Product

interface HomeRepository {

    suspend fun getCategories(): Resource<List<Category>>

    suspend fun getProducts(categoryId: Int, page: Int): Resource<MutableList<Product>>

}