package com.example.currency.data.datasource

import kotlinx.coroutines.flow.StateFlow

interface DataSource<type: Any> {
    val flow: StateFlow<type>
    suspend fun fetch()
}