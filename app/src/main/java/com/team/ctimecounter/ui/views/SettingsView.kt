package com.team.ctimecounter.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.team.ctimecounter.R
import com.team.ctimecounter.ui.components.AlertComponent
import com.team.ctimecounter.ui.components.DisplayThemeSpinnerComponent
import com.team.ctimecounter.ui.components.NavIconSizeSpinnerComponent
import com.team.ctimecounter.ui.components.NavViewSpinnerComponent
import com.team.ctimecounter.ui.components.TimeChainComponent
import com.team.ctimecounter.ui.util.chooseInfoImage

@Composable
fun SettingsView(settingsVM: SettingsVM, onChainTimeChanged: (String) -> Unit) {

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (themeLabel, themeSpinner, chainLabel, timeChainSpinner, infoBTN,
            sizeLabel, sizeSpinner, navLabel, navSpinner) = createRefs()
        val showDialog = remember { mutableStateOf(false) }

        if (showDialog.value) {
            AlertComponent(msg = "The time chain is used when it is necessary to repeat the specified time n times",
                showDialog = showDialog.value,
                onDismiss = {showDialog.value = false})
        }

        Text(text = stringResource(id = R.string.app_theme_label),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp)
                .constrainAs(themeLabel) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                })
        DisplayThemeSpinnerComponent(settingsVM.isDark.value, parentOptions = listOf("DarkTheme", "LightTheme"),
            modifier = Modifier
                .padding(start = 8.dp)
                .constrainAs(themeSpinner) {
                    start.linkTo(parent.start)
                    top.linkTo(themeLabel.bottom)
                }) { theme ->
                    settingsVM.switchTheme(theme)
                }
        Text(text = stringResource(id = R.string.app_chain_label),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(start = 8.dp, top = 16.dp)
                .constrainAs(chainLabel) {
                    start.linkTo(parent.start)
                    top.linkTo(themeSpinner.bottom)
                })

        Image(painter = painterResource(chooseInfoImage(settingsVM.isDark.value)),
            contentDescription = "",
            modifier = Modifier
                .padding(start = 8.dp)
                .clickable(true) {
                    showDialog.value = true
                }
                .constrainAs(infoBTN) {
                    start.linkTo(chainLabel.end)
                    top.linkTo(chainLabel.top)
                    bottom.linkTo(chainLabel.bottom)
                }
        )

        TimeChainComponent(
            vMultiplier = settingsVM.chainTime.value.split("x")[0],
            vTime = settingsVM.chainTime.value.split("x")[1],
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth(.9f)
                .constrainAs(timeChainSpinner) {
                    start.linkTo(parent.start)
                    top.linkTo(chainLabel.bottom)
                }) { timeChain ->
            // save time chain to dataStore
            settingsVM.setChainTime(timeChain ?: "1x1")
            onChainTimeChanged(timeChain ?: "1x1")
        }
        // Add bottom bar icon size picker 24-36-48
        Text(text = stringResource(id = R.string.app_icon_size_label),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(start = 8.dp, top = 16.dp)
                .constrainAs(sizeLabel) {
                    start.linkTo(parent.start)
                    top.linkTo(timeChainSpinner.bottom)
                })
        NavIconSizeSpinnerComponent(iconSize = settingsVM.iconSize.value,
            modifier = Modifier
            .padding(start = 8.dp, top = 8.dp)
            .constrainAs(sizeSpinner) {
                start.linkTo(parent.start)
                top.linkTo(sizeLabel.bottom)
            }) { iconSize ->
                settingsVM.switchIconSize(iconSize)
            }
        // add bottom bar view chooser -> classic(3 routes), modern(2 buttons + centered fab)
        Text(text = stringResource(id = R.string.app_navigation_view_label),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(start = 8.dp, top = 16.dp)
                .constrainAs(navLabel) {
                    start.linkTo(parent.start)
                    top.linkTo(sizeSpinner.bottom)
                })
        NavViewSpinnerComponent(navigationView = settingsVM.navigationView.value, modifier = Modifier
            .padding(start = 8.dp, top = 8.dp)
            .constrainAs(navSpinner) {
                start.linkTo(parent.start)
                top.linkTo(navLabel.bottom)
            }) { navView ->
                settingsVM.switchNavigationView(navView)
            }
        // IMPORTANT Todo set settingsVM to NOT pass app trough constructor
        //Pass ViewModel to child composables is a BAD practice.
        // You have to pass only values and callbacks that you need to avoid unnecessary recompositions and make your composables more reusable.

        //Todo Look if passable use common composable for 3 Spinners(Theme, IconSize and NavView)
    }
}