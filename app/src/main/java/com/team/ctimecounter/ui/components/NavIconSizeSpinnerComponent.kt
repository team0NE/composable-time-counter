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
fun NavIconSizeSpinnerComponent(app: BaseApplication, modifier: Modifier){
    val parentOptions = listOf("Small", "Medium", "Large")
    val expandedState = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf(
        when(app.iconSize.value) {
            "Small" -> parentOptions[0]
            "Medium" -> parentOptions[1]
            "Large" -> parentOptions[2]
            else -> parentOptions[0]
        }
    ) }

    ExposedDropdownMenuBox(expanded = expandedState.value, onExpandedChange = {
        expandedState.value = !expandedState.value
    }, modifier = modifier) {
        TextField(value = selectedOption.value,
            onValueChange = {
            },
            modifier = Modifier
                .menuAnchor(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedState.value) },
            readOnly = true)

        ExposedDropdownMenu(expanded = expandedState.value, onDismissRequest = {expandedState.value = false}) {
            parentOptions.forEach { option ->
                DropdownMenuItem(text = {
                    Text(text = option)
                }, onClick = {
                    selectedOption.value = option
                    expandedState.value = false
                    app.switchIconSize(option, false)
                })
            }
        }
    }
}