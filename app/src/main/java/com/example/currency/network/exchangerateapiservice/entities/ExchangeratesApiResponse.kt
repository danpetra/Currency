package com.example.currency.network.exchangerateapiservice.entities

data class ExchangeratesApiResponse(
    val success: Boolean,
    val timestamp: Int,
    val base: String,
    val date: String,
    val rates: Map<String, String>
)
