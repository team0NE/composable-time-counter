package com.team.ctimecounter.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.team.ctimecounter.ui.util.SnackbarController
import com.team.ctimecounter.ui.util.iconSizePicker
import com.team.ctimecounter.ui.views.CounterListVM
import com.team.ctimecounter.ui.views.CounterListView
import com.team.ctimecounter.ui.views.CounterVM
import com.team.ctimecounter.ui.views.CounterView
import com.team.ctimecounter.ui.views.SettingsVM
import com.team.ctimecounter.ui.views.SettingsView
import kotlinx.coroutines.launch

@Composable
fun Navigation(counterVM: CounterVM,
               counterListVM: CounterListVM,
               settingsVM: SettingsVM,
               navController: NavHostController,
               snackbarController: SnackbarController,
               snackbarHostState: SnackbarHostState){
    NavHost(navController = navController, startDestination = Routes.TimerRoute.route) {
        composable(route=Routes.TimerRoute.route) {
            CounterView(counterVM) { actionName ->
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
        composable(route = Routes.TimerListRoute.route) {
            CounterListView(listVM = counterListVM)
        }
        composable(route = Routes.SettingsRoute.route) {
            SettingsView(settingsVM = settingsVM) { testValue ->
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
fun ClassicBottomNavigationBar(items: List<BottomNavItem>,
                               chosenTab: Int,
                               navController: NavHostController,
                               modifier: Modifier,
                               onTabSelected: (Int) -> Unit
) {
    val selectedItem = remember { mutableStateOf(chosenTab) }
    NavigationBar(
        modifier = modifier
            .background(Color.DarkGray)
            .shadow(5.dp),
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem.value == index,
                onClick = {
                    selectedItem.value = index
                    navController.navigate(item.route)
                    onTabSelected(index)
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

@Composable
fun ModernBottomNavigationBar(items: List<BottomNavItem>,
                              chosenTab: Int,
                               iconSize: String,
                               navController: NavHostController,
                               onTabSelected: (Int) -> Unit
) {
    val selectedItem = remember { mutableStateOf(chosenTab) }

    BottomAppBar(actions = {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem.value == index,
                onClick = {
                    selectedItem.value = index
                    navController.navigate(item.route)
                    onTabSelected(index)
                },
                icon = {
                    Image(
                        painter = item.image,
                        contentDescription = "${item.name} Icon",
                    )
                })
        }
    },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Routes.SettingsRoute.route)
                selectedItem.value = 2
                onTabSelected(2)
            }) {
                Image(
                    painterResource(
                        iconSizePicker(iconSize,
                    Routes.SettingsRoute.route)
                    ), contentDescription = "")
            }
        },)
}