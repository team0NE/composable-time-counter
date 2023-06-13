package com.team.ctimecounter.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.team.ctimecounter.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeChainComponent(modifier: Modifier, onSaveClick: (String?) -> Unit) {
    val multiplier = remember {mutableStateOf("1")}
    val time = remember {mutableStateOf("1")}
    Row(modifier = modifier) {
        TimeChainSpinnerComponent(modifier = Modifier.width(100.dp)) { multValue ->
            multValue?.let { value ->
                multiplier.value = value
            } ?: "0"
        }
        Text(text = "x", modifier = Modifier.padding(16.dp))
        TextField(value = time.value, modifier = Modifier.width(100.dp), onValueChange = { timeValue ->
            time.value = timeValue
            // close keyboard
        })
        Button(modifier = Modifier.padding(start = 16.dp), onClick = {
            onSaveClick("${multiplier.value}x${time.value}")
        }) {
            Image( painter = painterResource(R.drawable.ic_save_black_24),
                contentDescription = "content description",
                contentScale = ContentScale.Crop)
        }
    }
}