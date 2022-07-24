package ass.cafeburp.dine.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ass.cafeburp.dine.data.local.daos.CartDao
import ass.cafeburp.dine.data.local.modals.CartItem

@Database(
    entities = [CartItem::class],
    version = 2,
    exportSchema = false
)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}