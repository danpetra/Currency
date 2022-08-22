package com.example.currency.di

import com.example.currency.network.exchangerateapiservice.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideRatesApi(): RatesApi = ExchangeratesApiService.invoke()
    @Singleton
    @Provides
    fun provideCurrenciesApi(): CurrenciesApi = ExchangeratesApiService.invoke()
}