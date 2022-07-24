package ass.cafeburp.dine.domain.modals

data class Order(
    private val name: String,
    private val mobile: String,
    private val table: String,
    private val order: Map<Int, Int>
)
