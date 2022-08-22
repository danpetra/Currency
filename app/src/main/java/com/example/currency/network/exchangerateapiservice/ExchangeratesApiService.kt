package com.example.currency.network.exchangerateapiservice

import com.example.currency.network.exchangerateapiservice.entities.ExchangeratesApiCurrencyResponse
import com.example.currency.network.exchangerateapiservice.entities.ExchangeratesApiResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeratesApiService: RatesApi, CurrenciesApi {
    @GET("latest")
    override suspend fun getLatest(
        @Query("base") base: String,
        @Query("apikey") apiKey: String
    ): ExchangeratesApiResponse

    @GET("symbols")
    override suspend fun getCurrencies(
        @Query("apikey") apiKey: String,
    ): ExchangeratesApiCurrencyResponse

    companion object{
        operator fun invoke(): ExchangeratesApiService{
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(ExchangeratesApiService::class.java)
        }
    }
}