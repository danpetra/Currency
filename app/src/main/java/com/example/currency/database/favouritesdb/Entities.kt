package com.example.currency.database.favouritesdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FAVOURITES_TABLE_NAME)
data class FavouriteEntity (
    @PrimaryKey val name: String,
)

@Entity(tableName = CURRENCIES_TABLE_NAME)
data class CurrencyEntity (
    @PrimaryKey val key: String,
    val name: String
)
