package com.example.currency.data.datasource

import com.example.currency.data.entities.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface RatesDataSource {
    suspend fun fetch(base: String)
    val flow: Flow<Map<String,String>>
    val statusFlow: StateFlow<Status>
}