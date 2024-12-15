package org.firstinspires.ftc.teamcode.nextFTCTesting

import com.qualcomm.robotcore.hardware.DcMotorEx
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup
import com.rowanmcalpin.nextftc.core.control.PIDController
import com.rowanmcalpin.nextftc.core.control.PIDFController
import com.rowanmcalpin.nextftc.core.control.coefficients.PIDCoefficients
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.ftc.hardware.MotorToPosition
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleMotorsToPosition

object LinearSlide: Subsystem {
    private lateinit var motor1: DcMotorEx
    private lateinit var motor2: DcMotorEx
    
    val inPos: Double = 0.0
    val outPos: Double = 1000.0
    
    val controller1: PIDController = PIDController(PIDCoefficients(0.005, 0.0, 0.0))
    val controller2: PIDController = PIDController(PIDCoefficients(0.005, 0.0, 0.0))
    
    override fun initialize() {
        motor1 = OpModeData.hardwareMap.get(DcMotorEx::class.java, "Motor")
    }
    
    val out: Command
        get() = MultipleMotorsToPosition(mapOf(
            Pair(motor1, controller1),
            Pair(motor2, controller2)
        ), outPos, setOf(this@LinearSlide))
    
    val toIn: Command
        get() = ParallelGroup(
            MotorToPosition(motor1, inPos, controller1, setOf(this@LinearSlide)),
            MotorToPosition(motor2, inPos, controller2, setOf())
        )
}