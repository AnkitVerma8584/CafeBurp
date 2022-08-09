package ass.cafeburp.dine.domain.modals.order

data class Order(
    private val name: String,
    private val mobile: String,
    private val table: String,
    private val total: Double,
    private val order: List<OrderItem>
)

