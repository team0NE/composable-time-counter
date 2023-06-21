package com.team.ctimecounter.ui.views

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.ctimecounter.data.CounterItem
import com.team.ctimecounter.ui.BaseApplication
import com.team.ctimecounter.utils.PrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CounterListVM
@Inject constructor
(app: BaseApplication, private val prefsManager: PrefsManager): ViewModel() {
    // mutableStateListOf used to show changed values in the list
    val countersViewList = mutableStateListOf<CounterItem>()
    // it's a shadow list to go through the countdown
    private val counterList = mutableListOf<CounterItem>()
    private val minutes = mutableStateOf(0)
    private val multiplier: MutableState<Int> = mutableStateOf(0)
    val isTimerRunning = mutableStateOf(false)
    private var counterJob: Job? = null

    init {
        viewModelScope.launch {
            // get saved string from dataStore
            val temp = "2x5"
//            val temp: String = prefsManager.getSavedTime().first().toString() ?: "2x35"
            // split string by x
            val timeList = temp.split("x")
            // rebuild func for view list
            minutes.value = timeList[1].toInt()
            multiplier.value = timeList[0].toInt()
            // list changes
            initList()
        }
    }

    fun convertToString(time: Int): String {
        val minutes = (time % 3600) / 60
        val seconds = time % 60

        // place it to util
        val minuteStr = if (minutes < 10) {
            "0$minutes"
        } else {
            minutes.toString()
        }

        val secondStr = if (seconds < 10) {
            "0$seconds"
        } else {
            seconds.toString()
        }

        return "$minuteStr:$secondStr"
    }
    private fun initList() {
        val value = multiplier.value
        repeat((0 until value).count()) {
            val counterItem = CounterItem(isFinished = minutes.value == 0, time = minutes.value)
            countersViewList.add(counterItem)
            counterList.add(counterItem)
        }
    }

    fun toggleCounter() {
        isTimerRunning.value = !isTimerRunning.value
        if (isTimerRunning.value) {
            startCounter()
        } else {
            clearListState()
        }
    }

    private fun startCounter() {
        counterJob = viewModelScope.launch {
            counterList.forEachIndexed { idx, counterItem ->
                // need a job cancel
                var newTime = counterItem.time
                while (newTime > 0) {
                    val changedTime = newTime - 1
                    delay(1000)
                    Log.d("TAGA", "counterItem.index: $idx")
                    Log.d("TAGA", "counterItem.time: ${counterItem.time}")
                    countersViewList[idx] = countersViewList[idx].copy(time = changedTime)
                    newTime = changedTime
                }
                countersViewList[idx] = countersViewList[idx].copy(isFinished = true)
            }
        }
    }

    private fun clearListState() {
        counterJob?.let { job ->
            job.cancel()
            counterJob = Job()
        }
        counterList.forEachIndexed { idx, _ ->
            countersViewList[idx] = countersViewList[idx].copy(isFinished = false)
            countersViewList[idx] = countersViewList[idx].copy(time = minutes.value)
        }
    }
}