package com.example.myapplication.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.model.Country

@Dao
interface CountryDao {
    @Insert
    suspend fun insertCountries(countries: List<Country>)

    @Query("SELECT * FROM Country")
    suspend fun getAllItems(): List<Country>?

    @Query("DELETE FROM Country")
    suspend fun delete()

    @Query("SELECT * FROM Country WHERE commonName LIKE '%' || :searchQuery || '%'")
    suspend fun searchCountry(searchQuery: String?): List<Country>

    suspend fun clearAndInsert(products: List<Country>) {
        delete()
        insertCountries(products)
    }
}