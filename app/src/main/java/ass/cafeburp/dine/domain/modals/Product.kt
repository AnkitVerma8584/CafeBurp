package ass.cafeburp.dine.domain.modals

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val discount: Double = 0.0,
    val image: String
)
