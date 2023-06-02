package com.team.ctimecounter.ui.presenter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.team.ctimecounter.R

@Composable
fun CounterView(vm: CounterVM) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (min, sep, sec, startBtn, saveBTN, resetBTN) = createRefs()

        Text(text = vm.sepView.value,
            style = TextStyle(
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.constrainAs(sep) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        })

        DigitView(value = vm.minutes.value, modifier = Modifier
            .padding(end = 8.dp)
            .constrainAs(min) {
                top.linkTo(sep.top)
                end.linkTo(sep.start)
                bottom.linkTo(sep.bottom)
            }) {
            vm.increaseTime(60)
        }

        DigitView(value = vm.seconds.value, modifier = Modifier
            .padding(start = 8.dp)
            .constrainAs(sec) {
                start.linkTo(sep.end)
                top.linkTo(sep.top)
                bottom.linkTo(sep.bottom)
            }) {
            vm.increaseTime(5)
        }

        Button(
            modifier = Modifier
                .requiredHeight(100.dp)
                .requiredWidth(100.dp)
                .clip(CircleShape)
                .constrainAs(startBtn) {
                    start.linkTo(parent.start)
                    top.linkTo(sep.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }, onClick = vm::toggleCounter) {
                    val img = if (vm.isTimerRunning.value) {
                        painterResource(R.drawable.ic_pause)
                    } else {
                        painterResource(R.drawable.ic_play)
                    }
                    Image(painter = img, modifier = Modifier.fillMaxSize(),
                        contentDescription = null, contentScale = ContentScale.Crop)
        }

        Button(
            modifier = Modifier
                .requiredHeight(64.dp)
                .requiredWidth(96.dp)
                .padding(8.dp)
                .constrainAs(saveBTN) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                },
            onClick = vm::saveRemainingTime) {
            Image( painter = painterResource(R.drawable.ic_save),
                contentDescription = "content description", contentScale = ContentScale.Crop)
        }

        Button(
            modifier = Modifier
                .requiredHeight(64.dp)
                .requiredWidth(96.dp)
                .padding(8.dp)
                .constrainAs(resetBTN) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            onClick = vm::resetRemainingTime) {
            Image( painter = painterResource(R.drawable.ic_reset),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "content description", contentScale = ContentScale.Crop)
        }
    }
}

// fix minus glitch