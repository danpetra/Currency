package com.example.currency.data.datasource

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.currency.database.favouritesdb.CurrencyEntity
import com.example.currency.database.favouritesdb.DATABASE_TAG
import com.example.currency.database.favouritesdb.RatesDao
import com.example.currency.network.exchangerateapiservice.API_SERVICE_TAG
import com.example.currency.network.exchangerateapiservice.CurrenciesApi
import com.example.currency.ui.APP_PREFERENCES
import com.example.currency.ui.APP_PREFERENCES_OVERRIDE_CURRENCIES
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Exception

class CurrencyCacheDataSource(
    context: Context,
    private val apiService: CurrenciesApi,
    private val dao: RatesDao
    ): DataSource<Map<String,String>> {

    private val _flow = MutableStateFlow<Map<String,String>>(mapOf())
    override val flow: StateFlow<Map<String, String>>
        get() = _flow

    private val preferences: SharedPreferences = context
        .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    private val overrideCurrencies = preferences
        .getBoolean(APP_PREFERENCES_OVERRIDE_CURRENCIES, true)

    override suspend fun fetch() {
        if (cacheContains()>0 && !overrideCurrencies){
            try {
                fetchFromDatabase()
            } catch (e: Exception){
                Log.e(DATABASE_TAG, "Error occurred while trying to read currencies: ${e.message}")
                fetchFromApi()
            }
        } else {
            fetchFromApi()
        }
    }

    private fun fetchFromDatabase(){
        _flow.value = dao.getAllCurrencies().associateBy ({it.key}, {it.name})
    }

    private suspend fun fetchFromApi(){
        _flow.value = try {
            apiService.getCurrencies()?.symbols?.also {
                try {
                    cache(it)
                } catch (e: Exception) {
                    Log.e(DATABASE_TAG, "Error occurred while trying to write currencies: ${e.message}")
                }
            } ?: mapOf()
        } catch (e: Exception) {
            Log.e(API_SERVICE_TAG, "Error occurred while trying to get currencies: ${e.message}")
            mapOf<String,String>()
        }
    }

    private fun cacheContains() = dao.getAllCurrencies().size.also { Log.i("Database","$it") }

    private fun cache(map: Map<String,String>){
        map.forEach{
            dao.insertCurrency(
                CurrencyEntity(
                    it.key,
                    it.value
                )
            )
        }
        preferences
            .edit()
            .putBoolean(APP_PREFERENCES_OVERRIDE_CURRENCIES, false)
            .apply()
    }
}