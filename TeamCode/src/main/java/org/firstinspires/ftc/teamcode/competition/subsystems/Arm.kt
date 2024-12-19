package org.firstinspires.ftc.teamcode.competition.subsystems

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.Servo
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition

@Config
object Arm: Subsystem {
    lateinit var servo: Servo
    
    @JvmField
    var name = "arm"

    @JvmField
    var intakePos = 0.05
    @JvmField
    var basketScorePos = 0.69
    @JvmField
    var autoParkPos = 0.65
    @JvmField
    var specimenPickupPos = 0.97
    @JvmField
    var specimenScorePos = 0.7
    
    val toIntake: Command
        get() = ServoToPosition(servo, intakePos, this)
    
    val toBasketScore: Command
        get() = ServoToPosition(servo, basketScorePos, this)
    
    val toAutoPark: Command
        get() = ServoToPosition(servo, autoParkPos, this)
    
    val toSpecimenPickup: Command
        get() = ServoToPosition(servo, specimenPickupPos, this)
    
    val toSpecimenScore: Command
        get() = ServoToPosition(servo, specimenScorePos, this)

    override fun initialize() {
        servo = OpModeData.hardwareMap.get(Servo::class.java, name)
    }
}