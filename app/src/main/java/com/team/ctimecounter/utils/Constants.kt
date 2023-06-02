package com.team.ctimecounter.utils

import androidx.datastore.preferences.core.intPreferencesKey

const val TAG = "AppDebug"
const val SHARED_PREF_KEY = "shared_prefs"
const val STATE_KEY_TIME = "recipe.state.time.key"
val savingTimeKey = intPreferencesKey(STATE_KEY_TIME)