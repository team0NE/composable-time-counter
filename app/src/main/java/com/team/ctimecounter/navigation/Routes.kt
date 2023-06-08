package com.team.ctimecounter.navigation

sealed class Routes(val route: String) {
    object TimerRoute: Routes("timer_route")
    object TimerListRoute: Routes("time_list_route")
    object SettingsRoute: Routes("settings_route")
}
