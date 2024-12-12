package com.rowanmcalpin.nextftc.core.command.utility.delays

import com.rowanmcalpin.nextftc.core.command.Command

/**
 * A [Command] that does nothing except wait until a certain amount of time has passed. Like all 
 * delays, if placed directly in a sequential group, it will accomplish nothing except slowing loop 
 * times and taking up memory.
 * @param time the desired duration of this command, in milliseconds
 */
class Delay(
    private val time: Long = 0L
): Command() {
    
    private var startTime: Long = 0L
    
    override val isDone: Boolean
        get() = System.currentTimeMillis() - startTime >= time

    override fun start() {
        startTime = System.currentTimeMillis()
    }
}