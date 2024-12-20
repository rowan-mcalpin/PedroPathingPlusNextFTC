package org.firstinspires.ftc.teamcode.nextFTCTesting.examplebucketauto

import com.qualcomm.robotcore.hardware.Servo
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition

object GrabSubsystem: Subsystem() {
    
    private lateinit var grab: Servo
    
    var closedClawPos = 0.25
    var openClawPos = 0.0
    
    val closeClaw: Command
        get() = ServoToPosition(grab, openClawPos, setOf(this@GrabSubsystem))
    val openClaw: Command
        get() = ServoToPosition(grab, closedClawPos, setOf(this@GrabSubsystem))

    override fun initialize() {
        grab = OpModeData.hardwareMap.get(Servo::class.java, "grab")
    }
}