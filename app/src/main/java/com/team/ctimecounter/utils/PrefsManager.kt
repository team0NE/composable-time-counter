package com.team.ctimecounter.utils

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class PrefsManager constructor(app: Application) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SHARED_PREF_KEY)
    private val dataStore : DataStore<Preferences> = app.applicationContext.dataStore

    suspend fun saveTime(value: Int) {
        dataStore.edit { pref ->
            pref[savingTimeKey] = value
        }
    }

    fun getSavedTime(): Flow<Int> {
        return dataStore.data.map { pref ->
            val time = pref[savingTimeKey] ?: 0
            time
        }
    }
}