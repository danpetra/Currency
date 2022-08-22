package com.example.currency.network.exchangerateapiservice

import android.content.Context
import android.util.Log
import com.example.currency.network.exchangerateapiservice.entities.ExchangeratesApiResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class StaticApi(private val appContext: Context): RatesApi {
    override suspend fun getLatest(base: String, apiKey: String): ExchangeratesApiResponse? {
        try {
            val jsonString = appContext.assets
                .open("json.json")
                .bufferedReader().use {
                    it.readText()
                }
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val jsonAdapter: JsonAdapter<ExchangeratesApiResponse> = moshi.adapter(
                ExchangeratesApiResponse::class.java
            )
            return jsonAdapter.fromJson(jsonString)

        } catch (e: Exception) {
            Log.e("PhotoApi", "In PhotoDataSource(Static): Query $base, Fetched Error ${e.message}")
            return null
        }
    }
}
