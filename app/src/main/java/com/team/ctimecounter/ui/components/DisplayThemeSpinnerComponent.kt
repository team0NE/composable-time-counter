package com.team.ctimecounter.ui.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.team.ctimecounter.ui.BaseApplication

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayThemeSpinnerComponent(app: BaseApplication, parentOptions: List<String>, modifier: Modifier) {
    val expandedState = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf(
        if (app.isDark.value) {
            parentOptions[0]
        } else {
            parentOptions[1]
        }
    )}

    ExposedDropdownMenuBox(expanded = expandedState.value, onExpandedChange = {
        expandedState.value = !expandedState.value
    }, modifier = modifier) {
        TextField(value = selectedOption.value,
            onValueChange = {
            },
            modifier = Modifier.menuAnchor(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedState.value) },
            readOnly = true)

        ExposedDropdownMenu(expanded = expandedState.value, onDismissRequest = {expandedState.value = false}) {
            parentOptions.forEach { option ->
                DropdownMenuItem(text = {
                    Text(text = option)
                }, onClick = {
                    selectedOption.value = option
                    expandedState.value = false
                    app.switchTheme(option, false)
                })
            }
        }
    }
}