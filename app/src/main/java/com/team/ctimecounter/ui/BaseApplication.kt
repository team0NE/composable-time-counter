package com.team.ctimecounter.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import com.team.ctimecounter.utils.PrefsManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication: Application() {
    // should be saved in datastore or cache
    var isDark = mutableStateOf(false)
    var iconSize = mutableStateOf("Small")
    private val applicationScope = MainScope()
    @Inject lateinit var prefsManager: PrefsManager

    init {
        applicationScope.launch {
            val theme = prefsManager.getSavedTheme().first() ?: "LightTheme"
            val iconSize = prefsManager.getSavedIconSize().first() ?: "Medium"
            switchTheme(theme, true)
            switchIconSize(iconSize, true)
        }
    }

    fun switchTheme(theme: String, isInitCall: Boolean) {
        isDark.value = theme == "DarkTheme"
        if (!isInitCall) {
            applicationScope.launch {
                prefsManager.saveTheme(theme)
            }
        }
    }
    fun switchIconSize(size: String, isInitCall: Boolean) {
        iconSize.value = size
        if (!isInitCall) {
            applicationScope.launch {
                prefsManager.saveIconSize(size)
            }
        }
    }
}