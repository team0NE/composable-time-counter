package com.team.ctimecounter.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import com.team.ctimecounter.utils.PrefsManager
import com.team.ctimecounter.utils.UpdateKey
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication: Application() {
    private val isDark = mutableStateOf(false)
    private val iconSize = mutableStateOf("Small")
    private val navigationView = mutableStateOf("Classic")
    private val applicationScope = MainScope()
    @Inject lateinit var prefsManager: PrefsManager

    init {
        applicationScope.launch {
            val theme = prefsManager.getSavedTheme().first() ?: "LightTheme"
            val iconSize = prefsManager.getSavedIconSize().first() ?: "Medium"
            val navView = prefsManager.getSavedNavigationView().first() ?: "Classic"
            switchTheme(theme)
            switchIconSize(iconSize)
            switchNavigationView(navView)
        }
    }

    private fun switchTheme(theme: String) {
        isDark.value = theme == "DarkTheme"
    }
    private fun switchIconSize(size: String) {
        iconSize.value = size
    }
    private fun switchNavigationView(view: String) {
        navigationView.value = view
    }

    fun updateView(key: UpdateKey, value: String) {
        when(key) {
            UpdateKey.ThemeKey -> {
                switchTheme(value)
            }
            UpdateKey.SizeKey -> {
                switchIconSize(value)
            }
            UpdateKey.NavKey -> {
                switchNavigationView(value)
            }
        }
    }
}