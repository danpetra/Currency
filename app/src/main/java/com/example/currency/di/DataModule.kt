package com.example.currency.di

import android.content.Context
import com.example.currency.data.repository.FavouritesRepository
import com.example.currency.data.repository.PopularRepository
import com.example.currency.data.controller.ViewController
import com.example.currency.data.datasource.*
import com.example.currency.database.favouritesdb.RatesDao
import com.example.currency.network.exchangerateapiservice.CurrenciesApi
import com.example.currency.network.exchangerateapiservice.RatesApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideFavouritesDbDataSource(dao: RatesDao) = FavouritesDbDataSource(dao)
    @Provides
    @Singleton
    fun provideRatesDataSource(apiService: RatesApi): RatesDataSource = RatesDataSourceImpl(apiService)
    @Provides
    @Singleton
    fun provideCurrenciesDataSource(
        context: Context,
        apiService: CurrenciesApi,
        dao : RatesDao
    ): DataSource<Map<String,String>> = CurrencyCacheDataSource(context, apiService, dao)
    @Provides
    @Singleton
    fun provideFavouritesRepository(
        dataSource : RatesDataSource,
        favouritesDataSource : FavouritesDbDataSource
    ) = FavouritesRepository(dataSource,favouritesDataSource)
    @Provides
    @Singleton
    fun providePopularRepository(
        dataSource : RatesDataSource,
        favouritesDataSource : FavouritesDbDataSource
    ) = PopularRepository(dataSource,favouritesDataSource)
    @Provides
    @Singleton
    fun provideUseCase(
        context: Context,
        favouritesDataSource: FavouritesDbDataSource,
        ratesDataSource: RatesDataSource,
        currencyDataSource: DataSource<Map<String,String>>
    ) = ViewController(context, favouritesDataSource, ratesDataSource, currencyDataSource)
}