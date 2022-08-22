package com.example.currency.network.exchangerateapiservice

import android.content.Context
import com.example.currency.network.exchangerateapiservice.entities.ExchangeratesApiCurrencyResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class CurStaticApi (private val appContext: Context): CurrenciesApi {

    override suspend fun getCurrencies(apiKey: String): ExchangeratesApiCurrencyResponse? {
        return try {
            val jsonString = appContext.assets
                .open("cur.json")
                .bufferedReader().use {
                    it.readText()
                }
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val jsonAdapter: JsonAdapter<ExchangeratesApiCurrencyResponse> =
                moshi.adapter(ExchangeratesApiCurrencyResponse::class.java)
            jsonAdapter.fromJson(jsonString)
        } catch (e: Exception) {
            null
        }
    }
}
