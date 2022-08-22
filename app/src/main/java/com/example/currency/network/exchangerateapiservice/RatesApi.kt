package com.example.currency.network.exchangerateapiservice

import com.example.currency.network.exchangerateapiservice.entities.ExchangeratesApiResponse

interface RatesApi {
    suspend fun getLatest( base: String, apiKey: String = API_KEY): ExchangeratesApiResponse?
}