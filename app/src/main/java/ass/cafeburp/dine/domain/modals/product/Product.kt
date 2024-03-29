package ass.cafeburp.dine.domain.modals.product

data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    val price: Double,
    val image: String
)
