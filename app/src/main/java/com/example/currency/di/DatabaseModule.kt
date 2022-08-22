package com.example.currency.di

import android.content.Context
import com.example.currency.database.favouritesdb.RatesDao
import com.example.currency.database.favouritesdb.RatesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideRatesDatabase(appContext: Context): RatesDatabase = RatesDatabase.getInstance(appContext)!!
    @Provides
    @Singleton
    fun provideRatesDao(database: RatesDatabase): RatesDao = database.ratesDao()
}