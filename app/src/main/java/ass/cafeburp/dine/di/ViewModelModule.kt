package ass.cafeburp.dine.di

import ass.cafeburp.dine.data.remote.implementations.HomeRepositoryImpl
import ass.cafeburp.dine.data.remote.implementations.OrderRepositoryImpl
import ass.cafeburp.dine.domain.repositories.HomeRepository
import ass.cafeburp.dine.domain.repositories.OrderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun provideHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    @ViewModelScoped
    abstract fun provideOrderRepository(orderRepositoryImpl: OrderRepositoryImpl): OrderRepository
}