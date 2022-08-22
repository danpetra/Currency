package com.example.currency.data.datasource

import com.example.currency.database.favouritesdb.FavouriteEntity
import com.example.currency.database.favouritesdb.RatesDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FavouritesDbDataSource(private val dao: RatesDao): FavouritesDataSource {

    private val _favouritesFlow = MutableStateFlow<List<FavouriteEntity>>(listOf())
    override val favouritesFlow: StateFlow<List<FavouriteEntity>>
        get() = _favouritesFlow

    override suspend fun fetchFavourites() {
        _favouritesFlow.value = dao.getAllFavourites()
    }

    override suspend fun updateFavourites(key: String) {
        val entity = FavouriteEntity(key)
        if (_favouritesFlow.value.contains(entity))
            deleteFavourite(entity)
        else
            addFavourite(entity)
    }

    private fun addFavourite(entity: FavouriteEntity){
        dao.insertFavourite(entity)
        _favouritesFlow.value = _favouritesFlow.value.plus(entity)
    }

    private fun deleteFavourite(entity: FavouriteEntity){
        dao.deleteFavourite(entity.name)
        _favouritesFlow.value = _favouritesFlow.value.minus(entity)
    }
}