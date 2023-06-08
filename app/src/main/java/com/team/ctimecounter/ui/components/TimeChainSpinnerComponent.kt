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
fun TimeChainSpinnerComponent(modifier: Modifier, onItemClick: (String?) -> Unit) {
    val parentOptions = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val expandedState = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf(parentOptions[0]) }

    ExposedDropdownMenuBox(expanded = expandedState.value, onExpandedChange = {
        expandedState.value = !expandedState.value
    }, modifier = modifier) {
        TextField(value = selectedOption.value.toString(),
            onValueChange = {
            },
            modifier = Modifier
                .menuAnchor(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedState.value) },
            readOnly = true)

        ExposedDropdownMenu(expanded = expandedState.value, onDismissRequest = {expandedState.value = false}) {
            parentOptions.forEach { option ->
                DropdownMenuItem(text = {
                    Text(text = option.toString())
                }, onClick = {
                    selectedOption.value = option
                    onItemClick(option.toString())
                    expandedState.value = false
                })
            }
        }
    }
}