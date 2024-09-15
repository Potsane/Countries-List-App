package com.example.myapplication.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface CountriesAppDataStore {
    suspend fun setCountriesPersistenceTimeStamp(time: Long)
    suspend fun getCountriesPersistenceTimeStamp(): Long
}

class CountriesAppDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : CountriesAppDataStore {

    private val timeStampKey = longPreferencesKey("timeStamp")
    override suspend fun setCountriesPersistenceTimeStamp(time: Long) {
        dataStore.edit { preferences ->
            preferences[timeStampKey] = time
        }
    }

    override suspend fun getCountriesPersistenceTimeStamp(): Long {
        return dataStore.data.first()[timeStampKey] ?: 0
    }
}