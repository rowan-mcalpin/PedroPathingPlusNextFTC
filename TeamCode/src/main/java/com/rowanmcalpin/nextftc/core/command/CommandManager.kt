package com.rowanmcalpin.nextftc.core.command

/**
 * This is the central controller for running commands in NextFTC. 
 */
object CommandManager {

    /**
     * List of actively running commands.
     */
    private val runningCommands = mutableListOf<Command>()

    /**
     * Commands that this controller needs to stop (remove from the [runningCommands] list).
     */
    private val commandsToStop = mutableListOf<Pair<Command, Boolean>>()

    /**
     * Initializes the [CommandManager]. This should be the first thing called in every OpMode.
     */
    fun init() {
        // There should be no commands running when the OpMode is initialized. Clear out all lists.
        runningCommands.clear()
    }

    /**
     * Updates the [CommandManager]. Should be called repeatedly during both initialization and during the OpMode.
     */
    fun update() {
        // First, stop any commands that needed stopping last update
        stopCommands()
        
        // Then, update (and stop) currently running commands
        runningCommands.forEach { command -> 
            try {
                // First thing we want to do is call the update() function on all of our commands
                command.update()
                
                // Then, if it's done, stop it
                if (command.isDone) {
                    stopCommand(command, false)
                }
            } catch (e: Exception) {
                // TODO: Handle command execution errors. Perhaps log to a command log file, then re-throw the exception? Or log + print to telemetry?
            }
        }
    }

    /**
     * Attempts to add a command to the running commands.
     * @param command the command to add
     * @return whether the command was able to be added
     */
    fun addCommand(command: Command): Boolean {
        val conflict = findConflicts(command)
        if (conflict == null) {
            // If there are no conflicts, just add this command
            runningCommands += command
        } else {
            if (conflict.interruptible) {
                stopCommand(conflict, true)
                runningCommands += command
            } else {
                // If there is a conflict, but it is not interruptible, we cannot add this command.
                return false
            }
        }
        
        // If we've gotten to this point, the command was successfully added. Now we need to call its
        // start() function before its update() function is run for the first time.
        command.start()
        
        // Now we can return true, because everything worked!
        return true
    }

    /**
     * MUST be called when the OpMode has been stopped.
     */
    fun stop() {
        // When the OpMode is stopped, we don't care if the Commands don't want to be done yet.
        forceStopAll()
    }

    /**
     * Force stops all active [Command]s. 
     */
    fun forceStopAll() {
        runningCommands.forEach { command ->
            stopCommand(command, true)
        }
    }

    /**
     * Stops all active [Command]s that are interruptible.
     */
    fun gentleStopAll() {
        runningCommands.forEach { command ->
            if (command.interruptible) {
                stopCommand(command, true)
            }
        }
    }

    /**
     * Finds [Subsystem] conflicts between a command and all of the running commands.
     * @param command the command to find conflicts with
     * @return the conflicting command, if there is one
     */
    fun findConflicts(command: Command): Command? {
        command.subsystems.forEach { subsystem ->
            runningCommands.forEach { otherCommand ->
                if (otherCommand.subsystems.contains(subsystem)) {
                    return otherCommand
                }
            }
        }
        
        return null
    }

    /**
     * Stops a [Command].
     * @param command the command to stop
     * @param interrupted whether the command stopped because it was finished, or because an incompatible command was scheduled
     */
    fun stopCommand(command: Command, interrupted: Boolean) {
        commandsToStop.add(Pair(command, interrupted))
    }

    /**
     * Stops any [Command]s that need stopping.
     */
    private fun stopCommands() {
        commandsToStop.forEach { 
            runningCommands.remove(it.first)
            it.first.stop(it.second)
        }
    }
}