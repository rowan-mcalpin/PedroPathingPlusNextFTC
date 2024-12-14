package com.rowanmcalpin.nextftc.core.command.utility.conditionals

import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.CommandManager

/**
 * This command behaves as an `if` statement, and schedules commands based on the result of the if 
 * statement. It is blocking, meaning `isDone` will not return `true` until the scheduled command 
 * has completed running.
 * @param condition the condition to reference
 * @param trueCommand the command to schedule if the reference is true
 * @param falseCommand the command to schedule if the reference is false
 */
class BlockingConditionalCommand(
    private val condition: () -> Boolean,
    private val trueCommand: Command,
    private val falseCommand: Command? = null
): Command() {
    
    private var result: Boolean? = null
    override val isDone: Boolean
        get() {
            if (result == null) return false
            if (result!!) return trueCommand.isDone
            return falseCommand?.isDone == true
        }

    override fun start() {
        if (condition.invoke()) {
            CommandManager.scheduleCommand(trueCommand)
            result = true
        } else {
            if (falseCommand != null) {
                CommandManager.scheduleCommand(falseCommand)
                result = false
            }
        }
    }
}