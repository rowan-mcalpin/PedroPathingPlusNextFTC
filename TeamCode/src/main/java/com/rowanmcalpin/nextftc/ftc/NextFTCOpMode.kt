package com.rowanmcalpin.nextftc.ftc

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.CommandManager
import com.rowanmcalpin.nextftc.ftc.controls.GamepadManager
import com.rowanmcalpin.nextftc.ftc.pedro.UpdateFollowerCommand
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower

/**
 * This is a wrapper class for an OpMode that does the following: 
 *  - Automatically initializes and runs the CommandManager
 *  - If desired, automatically implements and handles Gamepads
 *  - If desired, automatically updates the PedroPath Follower 
 */
open class NextFTCOpMode(vararg val subsystems: Subsystem = arrayOf(), autoCreateGamepadManager: Boolean = false): LinearOpMode() {
    
    open val follower: Follower? = null
    
    open val gamepadManager: GamepadManager? = if (autoCreateGamepadManager) GamepadManager(gamepad1, gamepad2) else null
    
    override fun runOpMode() {
        OpModeData.hardwareMap = hardwareMap
        OpModeData.gamepad1 = gamepad1
        OpModeData.gamepad2 = gamepad2
        
        CommandManager.init()
        onInit()
        
        // If we have a gamepad manager, add the command here
        if (gamepadManager != null) {
            CommandManager.addCommand(gamepadManager!!.GamepadUpdaterCommand())
        }
        
        if (follower != null) {
            CommandManager.addCommand(UpdateFollowerCommand())
        }
        
        // Wait for start
        while (!isStarted && !isStopRequested) {
            CommandManager.update()
            onWaitForStart()
        }
        
        // If we pressed stop after init (instead of start) we want to skip the rest of the OpMode
        // and jump straight to the end
        if (!isStopRequested) {
            onStartButtonPressed()
            
            while (!isStopRequested && isStarted) {
                CommandManager.update()
                onUpdate()
            }
        }
        
        onStop()
        CommandManager.stop()
    }
    
    open fun onInit() { }
    
    open fun onWaitForStart() { }
    
    open fun onStartButtonPressed() { }
    
    open fun onUpdate() { }
    
    open fun onStop() { }
}