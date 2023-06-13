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
import com.team.ctimecounter.navigation.BottomNavigationBar
import com.team.ctimecounter.navigation.Navigation
import com.team.ctimecounter.navigation.Routes
import com.team.ctimecounter.ui.BaseApplication
import com.team.ctimecounter.ui.views.CounterVM
import com.team.ctimecounter.ui.util.SnackbarController
import com.team.ctimecounter.ui.theme.CTimeCounterTheme
import com.team.ctimecounter.ui.util.iconSizePicker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var application: BaseApplication
    private val snackbarController = SnackbarController(lifecycleScope)
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm: CounterVM by viewModels()
            val snackbarHostState = remember { SnackbarHostState() }
            val navController = rememberNavController()

            CTimeCounterTheme(application.isDark.value) {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(name = Routes.TimerRoute.route, route = Routes.TimerRoute.route,
                                    image = painterResource(iconSizePicker(application.iconSize.value, Routes.TimerRoute.route))),
                                BottomNavItem(name = Routes.TimerListRoute.route, route = Routes.TimerListRoute.route,
                                    image = painterResource(iconSizePicker(application.iconSize.value, Routes.TimerListRoute.route))),
                                BottomNavItem(name = Routes.SettingsRoute.route, route = Routes.SettingsRoute.route,
                                    image = painterResource(iconSizePicker(application.iconSize.value, Routes.SettingsRoute.route)))
                            ),
                            navController = navController,
                            modifier = Modifier,
                            snackbarController = snackbarController,
                            snackbarHostState = snackbarHostState
                        )
                    }
                ){
                    Navigation(
                        app = application,
                        vm = vm,
                        navController = navController,
                        snackbarController = snackbarController,
                        snackbarHostState = snackbarHostState)
                }
            }
        }
    }
}