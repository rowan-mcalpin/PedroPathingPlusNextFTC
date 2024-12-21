package com.rowanmcalpin.nextftc.core.command.groups

import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command

/**
 * A command that schedules other commands at certain times. Inherits all subsystems of its children.
 */
abstract class CommandGroup(vararg commands: Command): Command() {
    init {
        commands.forEach { 
            children.add(it)
        }
    }

    /**
     * The collection of all commands within this group.
     */
    val children: MutableList<Command> = mutableListOf()
}