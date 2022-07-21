package ass.cafeburp.dine.di

import ass.cafeburp.dine.data.fake_data.FakeHomeRepo
import ass.cafeburp.dine.data.fake_data.FakeOrderRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FakeRepo {
    @Provides
    @Singleton
    fun provideFakeHomeRepo(): FakeHomeRepo = FakeHomeRepo()

    @Provides
    @Singleton
    fun provideFakeOrderRepo(): FakeOrderRepo = FakeOrderRepo()
}