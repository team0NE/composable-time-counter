package com.team.ctimecounter.ui.views

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
import com.team.ctimecounter.ui.components.DigitComponent

@Composable
fun CounterView(vm: CounterVM, showSnackBar: (action: String) -> Unit) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (min, sep, sec, startBtn, saveBTN, resetBTN) = createRefs()
        val topGuideLine = createGuidelineFromTop(.3f)
        val bottomGuideLine = createGuidelineFromBottom(.15f)

        Text(text = vm.sepView.value,
            style = TextStyle(
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.constrainAs(sep) {
            start.linkTo(parent.start)
            top.linkTo(topGuideLine)
            end.linkTo(parent.end)
        })

        DigitComponent(value = vm.minutes.value, modifier = Modifier
            .padding(end = 8.dp)
            .constrainAs(min) {
                top.linkTo(sep.top)
                end.linkTo(sep.start)
                bottom.linkTo(sep.bottom)
            }) {
            vm.increaseTime(60)
        }

        DigitComponent(value = vm.seconds.value, modifier = Modifier
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
                .requiredHeight(150.dp)
                .requiredWidth(150.dp)
                .clip(CircleShape)
                .constrainAs(startBtn) {
                    start.linkTo(parent.start)
                    top.linkTo(sep.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomGuideLine)
                },
            onClick = vm::toggleCounter) {
                    val img = if (vm.isTimerRunning.value) {
                        painterResource(R.drawable.ic_pause_24)
                    } else {
                        painterResource(R.drawable.ic_play_24)
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
                    bottom.linkTo(bottomGuideLine)
                },
            onClick = {
                vm.saveRemainingTime()
                showSnackBar("save")
            }) {
            Image( painter = painterResource(R.drawable.ic_save_black_24),
                contentDescription = "content description",
                contentScale = ContentScale.Crop)
        }

        Button(
            modifier = Modifier
                .requiredHeight(64.dp)
                .requiredWidth(96.dp)
                .padding(8.dp)
                .constrainAs(resetBTN) {
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomGuideLine)
                },
            onClick = {
                vm.resetRemainingTime()
                showSnackBar("reset")
            }) {
            Image( painter = painterResource(R.drawable.ic_reset_black_24),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "content description",
                contentScale = ContentScale.Crop)
        }
    }
}

// fix minus glitch