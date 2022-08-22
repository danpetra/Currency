package com.example.currency.data.datasource

import com.example.currency.database.favouritesdb.FavouriteEntity
import kotlinx.coroutines.flow.StateFlow

interface FavouritesDataSource {
    suspend fun fetchFavourites()
    suspend fun updateFavourites(key: String)
    val favouritesFlow: StateFlow<List<FavouriteEntity>>
}