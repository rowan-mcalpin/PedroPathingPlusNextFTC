package com.rowanmcalpin.nextftc.ftc.hardware

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.control.PIDFController
import kotlin.math.abs

/**
 * This implements a PID controller to drive a motor to a specified target velocity.
 *
 * @param motor the motor to control
 * @param targetVelocity the target velocity
 * @param controller the controller to implement
 * @param subsystems the list of subsystems this command interacts with (should be whatever
 *                      subsystem holds this command)
 * @param outCondition will be evaluated every update, and the command will stop once it returns true
 */
class MotorToVelocity(val motor: DcMotorEx, val targetVelocity: Double,
                      val controller: PIDFController, override val subsystems: Set<Subsystem>,
                      val outCondition: () -> Boolean = { abs(motor.velocity)-targetVelocity < 10 }): Command() {
    override val isDone: Boolean
        get() = outCondition.invoke()

    override fun start() {
        controller.target = targetVelocity
        controller.reset()
    }

    override fun update() {
        motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        motor.power = controller.calculate(motor.velocity)
    }
}