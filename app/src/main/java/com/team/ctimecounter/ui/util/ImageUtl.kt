package com.team.ctimecounter.ui.util

import com.team.ctimecounter.R

fun chooseInfoImage(isDark: Boolean): Int {
    return if (isDark) {
        R.drawable.ic_info_dark_24
    } else {
        R.drawable.ic_info_black_24
    }
}

fun iconSizePicker(size: String, name: String): Int {
    return when(size to name) {
        "Small" to "timer_route" -> R.drawable.ic_timer_black_24
        "Medium" to "timer_route" -> R.drawable.ic_timer_black_36
        "Large" to "timer_route" -> R.drawable.ic_timer_black_48

        "Small" to "time_list_route" -> R.drawable.ic_list_black_24
        "Medium" to "time_list_route" -> R.drawable.ic_list_black_36
        "Large" to "time_list_route" -> R.drawable.ic_list_black_48

        "Small" to "settings_route" -> R.drawable.ic_settings_black_24
        "Medium" to "settings_route" -> R.drawable.ic_settings_black_36
        "Large" to "settings_route" -> R.drawable.ic_settings_black_48

        else -> R.drawable.ic_info_black_24
    }
}