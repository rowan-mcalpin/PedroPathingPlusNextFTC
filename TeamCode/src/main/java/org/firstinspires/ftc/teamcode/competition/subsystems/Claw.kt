package org.firstinspires.ftc.teamcode.competition.subsystems

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.Servo
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition

@Config
object Claw: Subsystem {
    lateinit var servo: Servo
    
    @JvmField
    var name = "claw"

    @JvmField
    var openPos = 0.15
    @JvmField
    var closedPos = 0.35
    @JvmField
    var specimenOpenPos = 0.08
    
    val open: Command
        get() = ServoToPosition(servo, openPos, this)
    
    val close: Command
        get() = ServoToPosition(servo, closedPos, this)
    
    val specimenOpen: Command
        get() = ServoToPosition(servo, specimenOpenPos, this)

    override fun initialize() {
        servo = OpModeData.hardwareMap.get(Servo::class.java, name)
    }
}