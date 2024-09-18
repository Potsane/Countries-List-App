package com.example.myapplication.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.data.remote.model.Country

@Database(
    entities = [Country::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(CountriesAppEntityConverter::class)
abstract class CountriesAppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
}