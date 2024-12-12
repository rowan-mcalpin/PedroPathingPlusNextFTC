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
    protected val children: MutableList<Command> = mutableListOf()

    /**
     * Overrides the [Command.subsystems] variable to inherit all subsystems from all of its children.
     */
    override val subsystems: Set<Subsystem>
        get() = children.flatMap { it.subsystems }.toSet()

    /**
     * Sets whether this command is [interruptible]. This functionality is similar to a builder scheme,
     * so you can use it inline with the Command Group declaration.
     * @param interruptible whether this group should be interruptible
     * @return this Command Group, with [interruptible] set
     */
    abstract fun setInterruptible(interruptible: Boolean): CommandGroup
}