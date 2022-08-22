package com.example.currency.network.exchangerateapiservice

import com.example.currency.network.exchangerateapiservice.entities.ExchangeratesApiCurrencyResponse

interface CurrenciesApi {
    suspend fun getCurrencies(apiKey: String = API_KEY): ExchangeratesApiCurrencyResponse?
}