package com.team.ctimecounter.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CounterListItem(isFinished: Boolean, time: String) {
    if (isFinished) {
        Card(
            modifier = Modifier.padding(bottom = 16.dp),
            border = BorderStroke(2.dp, Color.Red)){
            Text(text = time, style = TextStyle(
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold
            ), modifier = Modifier.padding(all = 8.dp))
        }
    } else {
        Card(modifier = Modifier.padding(bottom = 16.dp)) {
            Text(
                text = time, style = TextStyle(
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(all = 8.dp))
        }
    }
}