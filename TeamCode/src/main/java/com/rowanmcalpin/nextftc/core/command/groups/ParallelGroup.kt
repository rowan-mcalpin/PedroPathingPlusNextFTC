package com.rowanmcalpin.nextftc.core.command.groups

import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.CommandManager

/**
 * A [CommandGroup] that runs all of its children simultaneously.
 */
class ParallelGroup(vararg commands: Command): CommandGroup(*commands) {
    /**
     * Private variable to be able to manually set interruptible during creation.
     */
    private var _interruptible = true

    override val interruptible
        get() = _interruptible
    
    /**
     * This will return false until all of its children are done
     */
    override val isDone: Boolean
        get() = children.all { it.isDone }

    /**
     * In a Parallel Group, we can just straight away add all of the commands to the CommandManager,
     * which can take care of the rest.
     */
    override fun start() {
        children.forEach { 
            CommandManager.addCommand(it)
        }
    }

    override fun setInterruptible(interruptible: Boolean): ParallelGroup {
        _interruptible = interruptible
        return this
    }
}