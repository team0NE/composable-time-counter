package com.team.ctimecounter

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.team.ctimecounter.navigation.BottomNavItem
import com.team.ctimecounter.navigation.ClassicBottomNavigationBar
import com.team.ctimecounter.navigation.ModernBottomNavigationBar
import com.team.ctimecounter.navigation.Navigation
import com.team.ctimecounter.navigation.Routes
import com.team.ctimecounter.ui.theme.CTimeCounterTheme
import com.team.ctimecounter.ui.util.SnackbarController
import com.team.ctimecounter.ui.util.hideKeyboard
import com.team.ctimecounter.ui.util.iconSizePicker
import com.team.ctimecounter.ui.views.CounterListVM
import com.team.ctimecounter.ui.views.CounterVM
import com.team.ctimecounter.ui.views.SettingsVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val snackbarController = SnackbarController(lifecycleScope)
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val counterVM: CounterVM by viewModels()
            val counterListVM: CounterListVM by viewModels()
            val settingsVM: SettingsVM by viewModels()
            val snackbarHostState = remember { SnackbarHostState() }
            val navController = rememberNavController()

            CTimeCounterTheme(settingsVM.isDark.value) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    bottomBar = {
                        if (settingsVM.navigationView.value == "Classic") {
                            ClassicBottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(name = Routes.TimerRoute.route, route = Routes.TimerRoute.route,
                                        image = painterResource(iconSizePicker(settingsVM.iconSize.value, Routes.TimerRoute.route))),
                                    BottomNavItem(name = Routes.TimerListRoute.route, route = Routes.TimerListRoute.route,
                                        image = painterResource(iconSizePicker(settingsVM.iconSize.value, Routes.TimerListRoute.route))),
                                    BottomNavItem(name = Routes.SettingsRoute.route, route = Routes.SettingsRoute.route,
                                        image = painterResource(iconSizePicker(settingsVM.iconSize.value, Routes.SettingsRoute.route)))
                                ),
                                chosenTab = settingsVM.chosenTab.value,
                                navController = navController,
                                modifier = Modifier
                            ) { idx ->
                                settingsVM.chosenTab.value = idx
                            }
                        } else {
                            ModernBottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(name = Routes.TimerRoute.route, route = Routes.TimerRoute.route,
                                        image = painterResource(iconSizePicker(settingsVM.iconSize.value, Routes.TimerRoute.route))),
                                    BottomNavItem(name = Routes.TimerListRoute.route, route = Routes.TimerListRoute.route,
                                        image = painterResource(iconSizePicker(settingsVM.iconSize.value, Routes.TimerListRoute.route)))
                                ),
                                chosenTab = settingsVM.chosenTab.value,
                                iconSize = settingsVM.iconSize.value,
                                navController = navController
                            ){ idx ->
                                settingsVM.chosenTab.value = idx
                            }
                        }

                    }
                ){
                    Navigation(
                        counterVM = counterVM,
                        counterListVM = counterListVM,
                        settingsVM = settingsVM,
                        navController = navController,
                        snackbarController = snackbarController,
                        snackbarHostState = snackbarHostState) {
                            hideKeyboard(this)
                    }
                }
            }
        }
    }
}