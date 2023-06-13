package com.team.ctimecounter.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DigitComponent(value: Int, modifier: Modifier, onClick: () -> Unit) {
    val str = if (value < 10) {
        "0$value"
    } else {
        value.toString()
    }

    Row(modifier = modifier) {
        Surface(
            modifier = Modifier.border(1.dp, Color.Red, shape = RoundedCornerShape(5.dp)).padding(8.dp),
        ) {
            Text(
                text = str,
                style = TextStyle(
                    fontSize = 96.sp,
                    fontWeight = FontWeight.Bold
                ), modifier = Modifier.clickable(true, onClick = onClick))
        }
    }
}