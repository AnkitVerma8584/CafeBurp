package ass.cafeburp.dine.domain.implementations

import ass.cafeburp.dine.data.remote.apis.HomeApi
import ass.cafeburp.dine.data.remote.repositories.HomeRepository
import ass.cafeburp.dine.data.test.FakeRepository
import ass.cafeburp.dine.domain.modals.Category
import ass.cafeburp.dine.domain.modals.Product
import ass.cafeburp.dine.util.Resource
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeApi: HomeApi) : HomeRepository {
    override suspend fun getCategories(): Resource<List<Category>> {
        return Resource.Success(data = FakeRepository.getCategories())
        try {
            val data = homeApi.getCategories()
            if (data.success)
                Resource.Success(data = data.data)
            else Resource.Error(message = data.message)
        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage ?: "Some unknown error occurred")
        }
    }

    override suspend fun getProducts(): Resource<List<Product>> {
        return try {
            val data = homeApi.getProducts()
            if (data.success)
                Resource.Success(data = data.data)
            else Resource.Error(message = data.message)
        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage ?: "Some unknown error occurred")
        }
    }
}