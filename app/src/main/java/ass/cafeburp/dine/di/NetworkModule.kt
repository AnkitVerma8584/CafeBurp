package ass.cafeburp.dine.di

import ass.cafeburp.dine.data.remote.apis.CartApi
import ass.cafeburp.dine.data.remote.apis.HomeApi
import ass.cafeburp.dine.data.remote.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    @CafeBurpRetrofitBuild
    fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(Api.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideHomeApiInterface(@CafeBurpRetrofitBuild retrofit: Retrofit): HomeApi =
        retrofit.create(HomeApi::class.java)

    @Provides
    @Singleton
    fun provideCartApiInterface(@CafeBurpRetrofitBuild retrofit: Retrofit): CartApi =
        retrofit.create(CartApi::class.java)

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CafeBurpRetrofitBuild
