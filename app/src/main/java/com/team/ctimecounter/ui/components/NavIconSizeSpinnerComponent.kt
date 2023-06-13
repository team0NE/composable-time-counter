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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavIconSizeSpinnerComponent(iconSize: String,
                                modifier: Modifier,
                                onSizePicked: (String) -> Unit
){
    val parentOptions = listOf("Small", "Medium", "Large")
    val expandedState = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf(
        when(iconSize) {
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
                    onSizePicked(option)
                })
            }
        }
    }
}