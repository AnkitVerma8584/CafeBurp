package ass.cafeburp.dine.data.remote.repositories

import ass.cafeburp.dine.data.remote.helpers.Resource

interface OrderRepository {

    suspend fun placeOrder(
        mobile: String,
        table: String
    ): Resource<String>
}