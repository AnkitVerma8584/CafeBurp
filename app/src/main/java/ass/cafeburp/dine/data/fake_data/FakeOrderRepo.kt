package ass.cafeburp.dine.data.fake_data

import ass.cafeburp.dine.data.remote.helpers.Resource
import ass.cafeburp.dine.data.remote.helpers.StringUtil
import ass.cafeburp.dine.data.remote.repositories.OrderRepository
import kotlinx.coroutines.delay
import kotlin.random.Random

class FakeOrderRepo : OrderRepository {

    override suspend fun placeOrder(mobile: String, table: String): Resource<String> {
        delay(5000)
        return if (!Random.nextBoolean())
            Resource.Success(data = "20 mins")
        else Resource.Error(StringUtil.DynamicText(FakeRepository.placeOrder()))
    }
}