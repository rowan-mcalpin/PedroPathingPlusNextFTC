package org.firstinspires.ftc.teamcode.competition.subsystems

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.utility.NullCommand
import com.rowanmcalpin.nextftc.core.control.PIDController
import com.rowanmcalpin.nextftc.core.control.PIDFController
import com.rowanmcalpin.nextftc.core.control.coefficients.PIDCoefficients
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.ftc.hardware.MotorToPosition
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleMotorsHoldPosition
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleMotorsToPosition
import kotlin.math.absoluteValue

@Config
object Lift: Subsystem {

    // Whenever we're not trying to move the lift, we want to hold its position
    override val defaultCommand: Command
        get() = holdPosition()
    
    lateinit var rightMotor: DcMotorEx // lift
    lateinit var leftMotor: DcMotorEx // lift2
    
    val rightController = PIDController(PIDCoefficients(0.005, 0.0, 0.0))
    val leftController = PIDController(PIDCoefficients(0.005, 0.0, 0.0))
    
    private var hanging = false
    
    @JvmField
    var intakePos = -10.0
    @JvmField
    var specimenPickupPos = 107.0
    @JvmField
    var highPos = 1387.0
    @JvmField
    var slightlyHighPos = 180.0
    @JvmField
    var specimenScorePos = 277.0
    @JvmField
    var specimenAutonomousScorePos = 277.0
    @JvmField
    var firstAutonomousSpecimenScorePos = 253.0
    @JvmField
    var hangPos = 720.0
    
    @JvmField
    var rightMotorName = "lift"
    @JvmField
    var leftMotorName = "lift2"
    
    val map = mapOf<DcMotorEx, PIDFController>(
        Pair(rightMotor, rightController), 
        Pair(leftMotor, leftController))
    
    val resetEncoders: Command
        get() {
            rightMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            leftMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            return NullCommand()
        }
    
    val toIntake: Command
        get() = MultipleMotorsToPosition(map, intakePos, this)
    
    val toSpecimenPickup: Command
        get() {
            hanging = false
            return MultipleMotorsToPosition(map, specimenPickupPos, this)
        }
    
    val toHigh: Command
        get() {
            hanging = false
            return MultipleMotorsToPosition(map, highPos, this)
        }
    
    val toSlightlyHigh: Command
        get() {
            hanging = false
            return MultipleMotorsToPosition(map, slightlyHighPos, this)
        }
    
    val toSpecimenScore: Command
        get() {
            hanging = false
            return MultipleMotorsToPosition(map, specimenScorePos, this)
        }
    
    val toAutonomousSpecScore: Command
        get() {
            hanging = false
            return MultipleMotorsToPosition(map, specimenAutonomousScorePos, this)
        }
    
    val toFirstAutonomousSpecScore: Command
        get() {
            hanging = false
            return MultipleMotorsToPosition(map, firstAutonomousSpecimenScorePos, this)
        }
    
    val toHang: Command
        get() {
            hanging = true
            return MultipleMotorsToPosition(map, firstAutonomousSpecimenScorePos, this)
        }
    
    private fun holdPosition(): Command {
        // If we're basically at the bottom of our ROM (and we're not hanging), we can safely depower the motors
        if (rightMotor.currentPosition.absoluteValue < 10 && !hanging) {
            rightMotor.power = 0.0
            leftMotor.power = 0.0
            return NullCommand()
        }
        // Otherwise, hold the current position
        return MultipleMotorsHoldPosition(map, this)
    }

    override fun initialize() {
        rightMotor = OpModeData.hardwareMap.get(DcMotorEx::class.java, rightMotorName)
        leftMotor = OpModeData.hardwareMap.get(DcMotorEx::class.java, leftMotorName)
    }
}