package ass.cafeburp.dine.data.fake_data

import ass.cafeburp.dine.R
import ass.cafeburp.dine.data.remote.helpers.Resource
import ass.cafeburp.dine.data.remote.helpers.StringUtil
import ass.cafeburp.dine.data.remote.repositories.HomeRepository
import ass.cafeburp.dine.domain.modals.Category
import ass.cafeburp.dine.domain.modals.Product
import kotlinx.coroutines.delay
import kotlin.random.Random

class FakeHomeRepo : HomeRepository {
    override suspend fun getCategories(): Resource<List<Category>> {
        delay(5000)
        return Resource.Success(data = FakeRepository.getCategories())
    }

    override suspend fun getProducts(): Resource<List<Product>> {
        delay(6000)
        return if (!Random.nextBoolean())
            Resource.Success(data = FakeRepository.getProducts())
        else Resource.Error(StringUtil.StringResource(R.string.some_error))
    }
}