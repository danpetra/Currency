package com.example.currency.data.controller

import android.content.Context
import android.util.Log
import com.example.currency.data.datasource.DataSource
import com.example.currency.data.datasource.FavouritesDbDataSource
import com.example.currency.data.datasource.RatesDataSource
import com.example.currency.ui.APP_PREFERENCES
import com.example.currency.ui.APP_PREFERENCES_BASE_CURRENCY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewController(
    context: Context,
    private val favouritesDataSource: FavouritesDbDataSource,
    private val ratesDataSource: RatesDataSource,
    private val curDataSource: DataSource<Map<String,String>>
) {
    var currentCurrency: String = context
        .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        .getString(APP_PREFERENCES_BASE_CURRENCY,"EUR") ?: "EUR"
    val flowOfCur: StateFlow<Map<String,String>> = curDataSource.flow

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        coroutineScope.launch {
            initFetch(currentCurrency)
            curDataSource.fetch()
        }
    }

    private suspend fun initFetch (initBase: String){
        ratesDataSource.fetch(initBase)
        favouritesDataSource.fetchFavourites()
    }

    suspend fun onBaseChanged(newBase: String){
        currentCurrency = newBase
        makeNewRequest(newBase)
    }

    suspend fun onFavouriteChanged(key: String){
        changeDatabase(key)
    }

    private suspend fun makeNewRequest(base: String){
        ratesDataSource.fetch(base)
    }

    private suspend fun changeDatabase(key: String) {
        favouritesDataSource.updateFavourites(key)
        favouritesDataSource.fetchFavourites()
    }

}