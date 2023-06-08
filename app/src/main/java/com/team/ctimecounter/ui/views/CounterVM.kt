package com.team.ctimecounter.ui.views

import android.media.MediaPlayer
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.ctimecounter.R
import com.team.ctimecounter.ui.BaseApplication
import com.team.ctimecounter.utils.PrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CounterVM
    @Inject constructor
    (app: BaseApplication, private val prefsManager: PrefsManager): ViewModel() {
    val minutes: MutableState<Int> = mutableStateOf(0)
    val seconds: MutableState<Int> = mutableStateOf(0)
    val sepView: MutableState<String> = mutableStateOf(":")
    val isTimerRunning = mutableStateOf(false)
    private val isSepShown = mutableStateOf(true)
    private var timeInSeconds: Int = 0
    private val tickingPlayer = MediaPlayer.create(app.applicationContext, R.raw.clock)
    private val finishPlayer = MediaPlayer.create(app.applicationContext, R.raw.stop)

    init {
        // get time from data storage
        initData()
    }

    private fun initData() {
        resetRemainingTime()
    }

    private suspend fun startCounter() {
        isTimerRunning.value = true
        timeInSeconds -= 1
        checkRemainingTime()
        playTicking()

        viewModelScope.launch {
            while (isTimerRunning.value && timeInSeconds > 0) {
                delay(1000)
                timeInSeconds -= 1
                checkRemainingTime()
            }
        }
    }

    private fun stopCounter() {
        isTimerRunning.value = false
        stopTicking()
    }

    private fun checkRemainingTime() {
        if (timeInSeconds > 0) {
            // do converting stuff
            converting(timeInSeconds)
        } else {
            //stop
            converting(0)
            stopTicking()
            playFinish()
            isSepShown.value = true
        }
    }

    private fun converting(totalSecs: Int) {
        minutes.value = (totalSecs % 3600) / 60
        seconds.value = totalSecs % 60

        isSepShown.value = !isSepShown.value
        sepView.value = if (isSepShown.value) ":" else " "
    }

    private fun playTicking() {
        tickingPlayer.isLooping = true
        tickingPlayer.start()
    }

    private fun stopTicking() {
        tickingPlayer.pause()
    }

    private fun playFinish() {
        finishPlayer.start()
    }

    fun toggleCounter() {
        viewModelScope.launch {
            if (!isTimerRunning.value) {
                startCounter()
            } else {
                stopCounter()
            }
        }
    }

    fun increaseTime(value: Int) {
        timeInSeconds += value
        checkRemainingTime()
    }

    fun saveRemainingTime() {
        viewModelScope.launch{
            prefsManager.saveTime(timeInSeconds)
        }
        // SnackBar "saved"
    }

    fun resetRemainingTime() {
        viewModelScope.launch {
            timeInSeconds = prefsManager.getSavedTime().first()
            converting(timeInSeconds)
        }
        // SnackBar "reset"
    }
}