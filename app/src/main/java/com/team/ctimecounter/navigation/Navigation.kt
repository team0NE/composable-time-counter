package com.team.ctimecounter.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.team.ctimecounter.ui.BaseApplication
import com.team.ctimecounter.ui.views.CounterVM
import com.team.ctimecounter.ui.views.CounterView
import com.team.ctimecounter.ui.views.SettingsView
import com.team.ctimecounter.ui.util.SnackbarController
import kotlinx.coroutines.launch

@Composable
fun Navigation(app: BaseApplication,
               vm: CounterVM,
               navController: NavHostController,
               snackbarController: SnackbarController,
               snackbarHostState: SnackbarHostState){
    NavHost(navController = navController, startDestination = Routes.TimerRoute.route) {
        composable(route=Routes.TimerRoute.route) {
            CounterView(vm) { actionName ->
                when(actionName) {
                    "save" -> {
                        snackbarController.getScope().launch {
                            snackbarController.showSnackbar(
                                snackbarHostState = snackbarHostState,
                                message = "Time mark saved as default",
                                actionLabel = "Ok"
                            )
                        }
                    }
                    "reset" -> {
                        snackbarController.getScope().launch {
                            snackbarController.showSnackbar(
                                snackbarHostState = snackbarHostState,
                                message = "Time mark reset to default",
                                actionLabel = "Ok"
                            )
                        }
                    }
                }
            }
        }
        composable(route = Routes.SettingsRoute.route) {
            SettingsView(app = app) { testValue ->
                snackbarController.getScope().launch {
                    snackbarController.showSnackbar(
                        snackbarHostState = snackbarHostState,
                        message = "Test value is: $testValue",
                        actionLabel = "Ok"
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(items: List<BottomNavItem>,
                        navController: NavHostController,
                        modifier: Modifier,
                        snackbarController: SnackbarController,
                        snackbarHostState: SnackbarHostState
) {
    val selectedItem = remember { mutableStateOf(0) }
    NavigationBar(
        modifier = modifier
            .background(Color.DarkGray)
            .shadow(5.dp),
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem.value == index,
                onClick = {
                    if (index == 1) {
                        snackbarController.getScope().launch {
                            snackbarController.showSnackbar(
                                snackbarHostState = snackbarHostState,
                                message = "This feature is under construction",
                                actionLabel = "Ok"
                            )
                        }
                    } else {
                        selectedItem.value = index
                        navController.navigate(item.route)
                    }
                },
                icon = {
                    Image(
                        painter = item.image,
                        contentDescription = "${item.name} Icon",
                    )
            })
        }
    }
}