package ass.cafeburp.dine.domain.modals

data class Order(
    private val name: String,
    private val mobile: String,
    private val table: String,
    private val total: Double,
    private val order: List<OrderItem>
)

data class OrderItem(private val id: Int, private val quantity: Int, private val price: Double)
