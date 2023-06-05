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
import androidx.lifecycle.lifecycleScope
import com.team.ctimecounter.ui.presenter.CounterVM
import com.team.ctimecounter.ui.presenter.CounterView
import com.team.ctimecounter.ui.presenter.util.SnackbarController
import com.team.ctimecounter.ui.theme.CTimeCounterTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val snackbarController = SnackbarController(lifecycleScope)
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm: CounterVM by viewModels()
            val snackbarHostState = remember { SnackbarHostState() }

            CTimeCounterTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) {
                    CounterView(vm) {actionName ->
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
            }
        }
    }
}