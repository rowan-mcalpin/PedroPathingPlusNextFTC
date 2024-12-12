package com.rowanmcalpin.nextftc.ftc.controls

import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.CommandManager
import com.rowanmcalpin.nextftc.core.command.utility.NullCommand

/**
 * A button control that has a value of true or false.
 * @param button the value of the button to watch
 */
class Button(private val button: () -> Boolean): Control() {
    /**
     * This command will be scheduled every time the button is pressed
     */
    var pressedCommand: () -> Command = { NullCommand() }

    /**
     * This command will be scheduled every time the button is released
     */
    var releasedCommand: () -> Command = { NullCommand() }

    /**
     * This command will be scheduled every update that the button is held down
     */
    var heldCommand: () -> Command = { NullCommand() }
    
    /**
     * This command will be scheduled whenever the state changes
     */
    var stateChangeCommand: () -> Command = { NullCommand() }

    override fun update() {
        updateState(button())
        
        if (state) {
            CommandManager.addCommand(heldCommand())
        }
        
        if (risingState) {
            CommandManager.addCommand(pressedCommand())
        }
        
        if (fallingState) {
            CommandManager.addCommand(releasedCommand())
        }
        
        if (stateChanged) {
            CommandManager.addCommand(stateChangeCommand())
        }
    }
}