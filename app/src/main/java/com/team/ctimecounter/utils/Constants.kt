package com.team.ctimecounter.utils

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

const val TAG = "AppDebug"
const val SHARED_PREF_KEY = "shared_prefs"
const val STATE_KEY_TIME = "recipe.state.time.key"
const val STATE_KEY_THEME = "recipe.state.theme.key"
// it can be "LightTheme", "DarkTheme" or "BlackRed"
val savingTimeKey = intPreferencesKey(STATE_KEY_TIME)
val savingThemeKey = stringPreferencesKey(STATE_KEY_THEME)