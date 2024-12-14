package org.firstinspires.ftc.teamcode.nextFTCTesting

import com.qualcomm.robotcore.hardware.DcMotorEx
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup
import com.rowanmcalpin.nextftc.core.control.PIDController
import com.rowanmcalpin.nextftc.core.control.coefficients.PIDCoefficients
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.ftc.hardware.MotorToPosition

object LinearSlide: Subsystem {
    private lateinit var motor: DcMotorEx
    
    val inPos: Double = 0.0
    val outPos: Double = 1000.0
    
    val controller: PIDController = PIDController(PIDCoefficients(0.005, 0.0, 0.0))
    
    override fun initialize() {
        motor = OpModeData.hardwareMap.get(DcMotorEx::class.java, "Motor")
    }
    
    val out: Command
        get() = ParallelGroup(
            MotorToPosition(motor, outPos, controller, setOf(this@LinearSlide))
        )
    
    val toIn: Command
        get() = ParallelGroup(
            MotorToPosition(motor, inPos, controller, setOf(this@LinearSlide))
        )
}