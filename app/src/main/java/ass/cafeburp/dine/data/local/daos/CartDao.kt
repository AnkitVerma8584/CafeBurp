package ass.cafeburp.dine.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ass.cafeburp.dine.data.local.modals.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert
    suspend fun addToCart(cartItem: CartItem)

    @Delete
    suspend fun removeFromCart(cartItem: CartItem)

    @Query("SELECT * FROM cart")
    fun getAllCartItems(): Flow<List<CartItem>>

    @Query("DELETE FROM cart")
    suspend fun emptyCart()

}