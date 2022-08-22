package com.example.currency.network.exchangerateapiservice.entities

data class ExchangeratesApiCurrencyResponse(
    val success: Boolean,
    val symbols: Map<String, String>
)
