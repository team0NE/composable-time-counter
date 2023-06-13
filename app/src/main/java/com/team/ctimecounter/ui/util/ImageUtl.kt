package com.team.ctimecounter.ui.util

import com.team.ctimecounter.R

fun chooseInfoImage(isDark: Boolean): Int {
    return if (isDark) {
        R.drawable.ic_info_dark_24
    } else {
        R.drawable.ic_info_black_24
    }
}