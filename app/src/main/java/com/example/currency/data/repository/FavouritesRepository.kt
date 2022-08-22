package com.example.currency.data.repository

import com.example.currency.data.datasource.FavouritesDataSource
import com.example.currency.data.datasource.RatesDataSource
import com.example.currency.data.entities.FinalEntity
import com.example.currency.database.favouritesdb.FavouriteEntity
import kotlinx.coroutines.flow.combine

class FavouritesRepository(
    dataSource : RatesDataSource,
    favouritesDataSource : FavouritesDataSource,
){
    private val ratesFlow = dataSource.flow
    private val favouritesFlow = favouritesDataSource.favouritesFlow

    val finalFlow = ratesFlow.combine(favouritesFlow) { p1, p2 ->
        makeFavouriteFinalEntity(p1, p2)
    }

    private fun makeFavouriteFinalEntity(
        ratesList: Map<String,String>?,
        favouritesList: List<FavouriteEntity>?
    ):List<FinalEntity> {
        return favouritesList?.map {
            FinalEntity(
                key = it.name,
                rate = ratesList?.get(it.name)?.toFloat() ?: 0f,
                isMarked = true
            )
        } ?: listOf()
    }
}