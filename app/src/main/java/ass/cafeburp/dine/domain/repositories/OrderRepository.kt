package ass.cafeburp.dine.domain.repositories

import ass.cafeburp.dine.domain.util.Resource
import ass.cafeburp.dine.domain.modals.order.Order

interface OrderRepository {

    suspend fun placeOrder(
        order: Order
    ): Resource<String>
}