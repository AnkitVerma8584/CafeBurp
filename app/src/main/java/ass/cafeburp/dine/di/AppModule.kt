package ass.cafeburp.dine.di

import android.app.Application
import androidx.room.Room
import ass.cafeburp.dine.data.local.MyRoomDatabase
import ass.cafeburp.dine.data.local.daos.CartDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        application: Application
    ): MyRoomDatabase =
        Room.databaseBuilder(
            application,
            MyRoomDatabase::class.java,
            "user_room_database"
        ).fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideUserDao(db: MyRoomDatabase): CartDao = db.cartDao()

}