package com.team.ctimecounter.ui.views

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.ctimecounter.ui.BaseApplication
import com.team.ctimecounter.utils.PrefsManager
import com.team.ctimecounter.utils.UpdateKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsVM
@Inject constructor
(private val app: BaseApplication,
 private val prefsManager: PrefsManager
 ): ViewModel() {
    // This is a hint to avoid auto choose of tab when theme is changed
    val chosenTab = mutableStateOf(0)
    var isDark: MutableState<Boolean> = mutableStateOf(false)
    val iconSize = mutableStateOf("Small")
    val navigationView = mutableStateOf("Classic")

    init {
        viewModelScope.launch {
            val theme = prefsManager.getSavedTheme().first() ?: "LightTheme"
            val iconSize = prefsManager.getSavedIconSize().first() ?: "Medium"
            val navView = prefsManager.getSavedNavigationView().first() ?: "Classic"
            switchTheme(theme)
            switchIconSize(iconSize)
            switchNavigationView(navView)
        }
    }

    fun switchTheme(theme: String) {
        isDark.value = theme == "DarkTheme"
        viewModelScope.launch{
            prefsManager.saveTheme(theme)
        }
        app.updateView(UpdateKey.ThemeKey, theme)
    }

    fun switchIconSize(size: String) {
        iconSize.value = size
        viewModelScope.launch {
            prefsManager.saveIconSize(size)
        }
        app.updateView(UpdateKey.SizeKey, size)
    }

    fun switchNavigationView(view: String) {
        navigationView.value = view
        viewModelScope.launch {
            prefsManager.saveNavigationView(view)
        }
        app.updateView(UpdateKey.NavKey, view)
    }
 }