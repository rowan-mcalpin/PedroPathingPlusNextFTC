package com.rowanmcalpin.nextftc.ftc

import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.FollowerNotInitializedException
import com.rowanmcalpin.nextftc.ftc.gamepad.GamepadEx
import com.rowanmcalpin.nextftc.ftc.gamepad.Joystick


/**
 * Uses the joystick inputs to drive the robot
 *
 * @param driveJoystick The joystick to use for forward and strafe movement
 * @param turnJoystick The joystick to use for turning
 * @param robotCentric Whether to use robot centric or field centric movement
 * @param subsystems The list of subsystems this command interacts with (should be whatever
 *  *                      subsystem holds this command)
 */
class DriverControlled(val driveJoystick: Joystick, val turnJoystick: Joystick, val robotCentric: Boolean, override val subsystems: Set<Subsystem>): Command() {

    /**
     * @param driveJoystick The joystick to use for forward and strafe movement
     * @param turnJoystick The joystick to use for turning
     * @param robotCentric Whether to use robot centric or field centric movement
     * @param subsystem The subsystem this command interacts with
     */
    constructor(driveJoystick: Joystick, turnJoystick: Joystick, robotCentric: Boolean, subsystem: Subsystem): this(driveJoystick, turnJoystick, robotCentric, setOf(subsystem))

    /**
     * @param driveJoystick The joystick to use for forward and strafe movement
     * @param turnJoystick The joystick to use for turning
     * @param subsystems The list of subsystems this command interacts with (should be whatever
     *  *                      subsystem holds this command)
     */
    constructor(driveJoystick: Joystick, turnJoystick: Joystick, subsystems: Set<Subsystem>): this(driveJoystick, turnJoystick, true, subsystems)

    /**
     * @param driveJoystick The joystick to use for forward and strafe movement
     * @param turnJoystick The joystick to use for turning
     * @param subsystem The subsystem this command interacts with
     */
    constructor(driveJoystick: Joystick, turnJoystick: Joystick, subsystem: Subsystem): this(driveJoystick, turnJoystick, true, setOf(subsystem))

    /**
     * @param gamepad The gamepad to use the joysticks from
     * @param robotCentric Whether to use robot centric or field centric movement
     * @param subsystems The list of subsystems this command interacts with (should be whatever
     *  *                     subsystem holds this command)
     */
    constructor(gamepad: GamepadEx, robotCentric: Boolean, subsystems: Set<Subsystem>): this(gamepad.leftStick, gamepad.rightStick, robotCentric, subsystems)

    /**
     * @param gamepad The gamepad to use the joysticks from
     * @param robotCentric Whether to use robot centric or field centric movement
     * @param subsystem The subsystem this command interacts with
     */
    constructor(gamepad: GamepadEx, robotCentric: Boolean, subsystem: Subsystem): this(gamepad.leftStick, gamepad.rightStick, robotCentric, setOf(subsystem))

    /**
     * @param gamepad The gamepad to use the joysticks from
     * @param subsystems The list of subsystems this command interacts with (should be whatever
     *  *                      subsystem holds this command)
     */
    constructor(gamepad: GamepadEx, subsystems: Set<Subsystem>): this(gamepad.leftStick, gamepad.rightStick, true, subsystems)

    /**
     * @param gamepad The gamepad to use the joysticks from
     * @param subsystem The subsystem this command interacts with
     */
    constructor(gamepad: GamepadEx, subsystem: Subsystem): this(gamepad.leftStick, gamepad.rightStick, true, setOf(subsystem))


    override val isDone: Boolean = false

    override fun start() {
        if (OpModeData.follower == null) throw FollowerNotInitializedException()
        OpModeData.follower!!.startTeleopDrive()
    }


    override fun update() {
        OpModeData.follower!!.setTeleOpMovementVectors(driveJoystick.y.toDouble(),
            driveJoystick.x.toDouble(), turnJoystick.x.toDouble(), robotCentric)
    }
}