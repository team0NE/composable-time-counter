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
import com.team.ctimecounter.ui.BaseApplication
import com.team.ctimecounter.ui.components.AlertComponent
import com.team.ctimecounter.ui.components.DisplayThemeSpinnerComponent
import com.team.ctimecounter.ui.components.TimeChainComponent

@Composable
fun SettingsView(app: BaseApplication, showSnackbar: (String) -> Unit) {

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (themeLabel, themeSpinner, chainLabel, languageSpinner, infoBTN) = createRefs()
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
        DisplayThemeSpinnerComponent(app, parentOptions = listOf("Dark", "Light"),
            modifier = Modifier
                .padding(start = 8.dp)
                .constrainAs(themeSpinner) {
                    start.linkTo(parent.start)
                    top.linkTo(themeLabel.bottom)
                })
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

        Image(painter = painterResource(id = R.drawable.ic_info_black_24),
            contentDescription = "",
            modifier = Modifier
                .padding(start = 8.dp)
                .clickable(true) {
                    showDialog.value = true
                }
                .constrainAs(infoBTN){
                start.linkTo(chainLabel.end)
                top.linkTo(chainLabel.top)
                bottom.linkTo(chainLabel.bottom)
            }
        )

        TimeChainComponent(
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth(.9f)
                .constrainAs(languageSpinner) {
                    start.linkTo(parent.start)
                    top.linkTo(chainLabel.bottom)
                }) { timeChain ->
            showSnackbar(timeChain.toString())
            // save time chain to dataStore
        }
    }
}