package com.example.currency.data.entities

data class FinalEntity(
    val key: String,
    val rate: Float,
    val name: String = key,
    val isMarked: Boolean = false
)
