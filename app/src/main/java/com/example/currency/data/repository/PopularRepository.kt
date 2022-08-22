package com.example.currency.data.repository

import com.example.currency.data.datasource.FavouritesDataSource
import com.example.currency.data.datasource.RatesDataSource
import com.example.currency.data.entities.FinalEntity
import com.example.currency.database.favouritesdb.FavouriteEntity
import kotlinx.coroutines.flow.combine

class PopularRepository (
    dataSource : RatesDataSource,
    favouritesDataSource : FavouritesDataSource,
){
    private val ratesFlow = dataSource.flow
    private val favouritesFlow = favouritesDataSource.favouritesFlow

    val statusFlow = dataSource.statusFlow

    val finalFlow = ratesFlow.combine(favouritesFlow) { p1, p2 ->
        makePopularFinalEntity(p1, p2)
    }

    private fun makePopularFinalEntity(
        ratesList: Map<String,String>?,
        favouritesList: List<FavouriteEntity>?
    ):List<FinalEntity> {
        return ratesList?.map { entry->
            FinalEntity(
                entry.key,
                entry.value.toFloat(),
                isMarked = favouritesList?.map { it.name }?.contains(entry.key) ?: false
            )
        } ?: listOf()
    }
}