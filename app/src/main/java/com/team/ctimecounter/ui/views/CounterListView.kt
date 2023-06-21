package com.team.ctimecounter.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.team.ctimecounter.R
import com.team.ctimecounter.ui.components.CounterListItem

@Composable
fun CounterListView(listVM: CounterListVM) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (list, btn) = createRefs()
        val bottomGuideLine = createGuidelineFromBottom(.3f)
        val bottomBoarderGuideLine = createGuidelineFromBottom(.15f)

        LazyColumn(modifier = Modifier.constrainAs(list){
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            bottom.linkTo(bottomGuideLine)
        }) {
//            itemsIndexed(items = listVM.countersList) { _, itm ->
//                CounterListItem(isFinished = itm.isFinished, time = listVM.convertToString(itm.time))
//            }
            items(listVM.countersViewList) { itm ->
                CounterListItem(isFinished = itm.isFinished, time = listVM.convertToString(itm.time))
            }
        }

        Button(onClick =  listVM::toggleCounter, modifier = Modifier
            .requiredHeight(64.dp)
            .requiredWidth(96.dp)
            .padding(8.dp)
            .constrainAs(btn) {
                end.linkTo(parent.end)
                bottom.linkTo(bottomBoarderGuideLine)
            }) {
            val img = if (listVM.isTimerRunning.value) {
                painterResource(R.drawable.ic_pause_24)
            } else {
                painterResource(R.drawable.ic_play_24)
            }
            Image(painter = img, contentDescription = null, contentScale = ContentScale.Crop)
        }
    }
}

/*
Divider(
modifier = Modifier.constrainAs(div) {
    bottom.linkTo(topGuideLine)
},
color = Color.Blue,
thickness = 1.dp)
*/