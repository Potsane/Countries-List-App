package com.example.myapplication.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.example.myapplication.data.local.database.CountriesAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okio.Path.Companion.toPath

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideCountriesAppDatabase(
        @ApplicationContext context: Context,
    ): CountriesAppDatabase {
        return Room.databaseBuilder(
            context,
            CountriesAppDatabase::class.java,
            "CountriesApp-DB"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        val path = appContext.filesDir.resolve("app.preferences_pb").absolutePath
        return PreferenceDataStoreFactory.createWithPath(
            scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob()),
            corruptionHandler = null,
            migrations = emptyList(),
            produceFile = { path.toPath() },
        )
    }

    @Provides
    fun provideCountryDao(database: CountriesAppDatabase) = database.countryDao()
}