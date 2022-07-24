package ass.cafeburp.dine.data.remote.repositories

import ass.cafeburp.dine.data.remote.helpers.Resource
import ass.cafeburp.dine.domain.modals.Order

interface OrderRepository {

    suspend fun placeOrder(
        order: Order
    ): Resource<String>
}