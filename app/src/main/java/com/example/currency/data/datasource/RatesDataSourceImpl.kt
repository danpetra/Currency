package com.example.currency.data.datasource

import android.util.Log
import com.example.currency.data.entities.Status
import com.example.currency.network.exchangerateapiservice.RatesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RatesDataSourceImpl(
    private val apiService: RatesApi
    ) : RatesDataSource {

    private val _flow = MutableStateFlow<Map<String,String>>(mapOf())
    override val flow: StateFlow<Map<String, String>>
        get() = _flow

    private val _statusFlow = MutableStateFlow<Status>(Status.LOADING)
    override val statusFlow: StateFlow<Status>
        get() = _statusFlow

    override suspend fun fetch(base: String) {
        try{
            _flow.value = apiService.getLatest(base = base)?.rates ?:
                throw java.lang.Exception("Empty body")

            _statusFlow.value = Status.SUCCESS
        } catch (e: Exception){
            _statusFlow.value = Status.ERROR(e.message ?: "Unknown error while loading rates")
            Log.i("RatesDataSource","Error ${e.message}")
        }

    }
}