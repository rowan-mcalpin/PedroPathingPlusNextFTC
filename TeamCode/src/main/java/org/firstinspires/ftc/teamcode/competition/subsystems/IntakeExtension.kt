package org.firstinspires.ftc.teamcode.competition.subsystems

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.utility.NullCommand
import com.rowanmcalpin.nextftc.core.control.PIDController
import com.rowanmcalpin.nextftc.core.control.coefficients.PIDCoefficients
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.ftc.hardware.MotorHoldPosition
import com.rowanmcalpin.nextftc.ftc.hardware.MotorToPosition

@Config
object IntakeExtension: Subsystem {
    override val defaultCommand = holdPosition
    
    lateinit var motor: DcMotorEx
    
    val controller = PIDController(PIDCoefficients(0.005, 0.0, 0.0))

    @JvmField
    var transferPos = 69.0
    @JvmField
    var outPos = 790.0 // TODO
    @JvmField
    var slightlyOutPos = 300.0 // TODO
    @JvmField
    var middlePos = 600.0
    
    @JvmField
    var motorName = "intake_extension"
    
    val resetEncoders: Command
        get() {
            motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            return NullCommand()
        }
    
    val toTransfer: Command
        get() = MotorToPosition(motor, transferPos, controller, this)
    
    val toOut: Command
        get() = MotorToPosition(motor, outPos, controller, this)
    
    val toSlightlyOut: Command
        get() = MotorToPosition(motor, slightlyOutPos, controller, this)
    
    val toMiddlePos: Command
        get() = MotorToPosition(motor, middlePos, controller, this)

    val holdPosition: Command
        get() = MotorHoldPosition(motor, controller, this)

    override fun initialize() {
        motor = OpModeData.hardwareMap.get(DcMotorEx::class.java, motorName)
    }
}