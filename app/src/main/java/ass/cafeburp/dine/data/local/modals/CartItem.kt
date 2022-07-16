package ass.cafeburp.dine.data.local.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey
    val id: Int,
    val name: String,
    val quantity: Int,
    val price: Double,
    val discount: Double = 0.0,
    val image: String
) {
    val totalPrice get() = quantity * (price - discount)
}
