package com.example.currency.database.favouritesdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RatesDao {
    @Insert
    fun insertFavourite(rate: FavouriteEntity)
    @Query("DELETE FROM $FAVOURITES_TABLE_NAME")
    fun clearAllFavourites()
    @Query("DELETE FROM $FAVOURITES_TABLE_NAME WHERE `name` = :key")
    fun deleteFavourite(key: String)
    @Query("SELECT * FROM $FAVOURITES_TABLE_NAME")
    fun getAllFavourites():List<FavouriteEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrency(Currency: CurrencyEntity)
    @Query("DELETE FROM $CURRENCIES_TABLE_NAME")
    fun clearAllCurrencies()
    @Query("SELECT * FROM $CURRENCIES_TABLE_NAME")
    fun getAllCurrencies():List<CurrencyEntity>
}
