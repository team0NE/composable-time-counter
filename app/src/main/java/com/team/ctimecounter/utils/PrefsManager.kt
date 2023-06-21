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

    suspend fun saveTheme(value: String) {
        dataStore.edit { pref ->
            pref[savingThemeKey] = value
        }
    }
    suspend fun saveIconSize(value: String) {
        dataStore.edit { pref ->
            pref[savingIconSizeKey] = value
        }
    }
    suspend fun saveNavigationView(view: String) {
        dataStore.edit { pref ->
            pref[savingNavigationViewKey] = view
        }
    }

    suspend fun saveChainTime(chain: String) {
        dataStore.edit { pref ->
            pref[savingChainTimeKey] = chain
        }
    }

    fun getSavedTheme(): Flow<String?> {
        return dataStore.data.map { pref ->
            val theme = pref[savingThemeKey] ?: "LightTheme"
            theme
        }
    }
    fun getSavedIconSize(): Flow<String?> {
        return dataStore.data.map { pref ->
            val iconSize = pref[savingIconSizeKey] ?: "Medium"
            iconSize
        }
    }
    fun getSavedNavigationView(): Flow<String?> {
        return dataStore.data.map { pref ->
            val iconSize = pref[savingNavigationViewKey] ?: "Classic"
            iconSize
        }
    }

    fun getSavedChain(): Flow<String?> {
        return dataStore.data.map { pref ->
            val chainTime = pref[savingChainTimeKey] ?: "1x1"
            chainTime
        }
    }
}