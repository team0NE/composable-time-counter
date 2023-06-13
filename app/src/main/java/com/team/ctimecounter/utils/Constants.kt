package com.team.ctimecounter.utils

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

const val TAG = "AppDebug"
const val SHARED_PREF_KEY = "shared_prefs"
const val STATE_KEY_TIME = "state.time.key"
const val STATE_KEY_THEME = "state.theme.key"
const val STATE_KEY_ICON_SIZE = "state.icon.size.key"
const val STATE_KEY_NAVIGATION_VIEW = "state.navigation.view.key"
val savingTimeKey = intPreferencesKey(STATE_KEY_TIME)
// it can be "LightTheme" or "DarkTheme"
val savingThemeKey = stringPreferencesKey(STATE_KEY_THEME)
// it can be "Small", "Medium" or "Large"
val savingIconSizeKey = stringPreferencesKey(STATE_KEY_ICON_SIZE)
// it can be "Classic" or "Modern"
val savingNavigationViewKey = stringPreferencesKey(STATE_KEY_NAVIGATION_VIEW)