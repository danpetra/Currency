package com.example.currency.database.favouritesdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavouriteEntity::class, CurrencyEntity::class], version = 1, exportSchema = false)
abstract class RatesDatabase: RoomDatabase() {
    abstract fun ratesDao(): RatesDao
    companion object {
        @Volatile
        private var INSTANCE: RatesDatabase? = null
        fun getInstance(appContext: Context): RatesDatabase? {
            synchronized(RatesDatabase::class) {
                var instance = INSTANCE
                if (INSTANCE == null) {
                    instance = Room.databaseBuilder(
                        appContext,
                        RatesDatabase::class.java,
                        RATES_DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
