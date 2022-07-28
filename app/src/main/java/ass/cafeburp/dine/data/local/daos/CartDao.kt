package ass.cafeburp.dine.data.local.daos

import androidx.room.*
import ass.cafeburp.dine.data.local.modals.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cartItem: CartItem)

    @Query("UPDATE cart SET quantity = quantity - 1 WHERE id=:id;")
    suspend fun decreaseItem(id: Int)

    @Query("UPDATE cart SET quantity = quantity + 1 WHERE id=:id;")
    suspend fun increaseItem(id: Int)

    @Delete
    suspend fun removeFromCart(cartItem: CartItem)

    @Query("SELECT * FROM cart;")
    fun getAllCartItems(): Flow<List<CartItem>>

    @Query("SELECT * FROM cart;")
    suspend fun getItemsForOrder(): List<CartItem>

    @Query("DELETE FROM cart;")
    suspend fun emptyCart()

    @Query("SELECT SUM(price*quantity) as total FROM cart;")
    suspend fun getTotal(): Double

    @Query("SELECT EXISTS(SELECT * FROM cart WHERE id=:id LIMIT 1);")
    suspend fun isItemExists(id: Int): Boolean

}