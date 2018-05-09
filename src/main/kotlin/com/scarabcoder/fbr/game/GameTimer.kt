package com.scarabcoder.fbr.game

import com.scarabcoder.commons.task
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class GameTimer private constructor(var duration: Long) {

    var startedAt: Long = System.currentTimeMillis()

    fun setDuration(time: Long, unit: TimeUnit) {
        duration = unit.toMillis(time)
    }

    fun setTimeLeft(time: Long, unit: TimeUnit) {
        setDuration(System.currentTimeMillis() - (duration) + unit.toMillis(time), unit)
    }

    fun getTimeLeft(unit: TimeUnit): Long {
        return TimeUnit.MILLISECONDS.convert(duration - (System.currentTimeMillis() - startedAt), unit)
    }


    companion object {

        fun startTimer(duration: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, onTimerEnd: () -> Unit): GameTimer {

            val timer = GameTimer(timeUnit.toMillis(duration))

            thread {
                check@while(true) {

                    if(timer.getTimeLeft(TimeUnit.MILLISECONDS) <= 0) {
                        task {
                            onTimerEnd()
                        }
                        return@check
                    }

                }
            }
            return timer

        }

    }

}