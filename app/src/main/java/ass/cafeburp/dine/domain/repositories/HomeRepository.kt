package ass.cafeburp.dine.domain.repositories

import ass.cafeburp.dine.domain.util.Resource
import ass.cafeburp.dine.domain.modals.category.Category
import ass.cafeburp.dine.domain.modals.product.Product

interface HomeRepository {

    suspend fun getCategories(): Resource<List<Category>>

    suspend fun getProducts(categoryId: Int, page: Int): Resource<MutableList<Product>>

}