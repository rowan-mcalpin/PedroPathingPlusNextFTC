package org.firstinspires.ftc.teamcode.competition.subsystems

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.Servo
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition

@Config
object Intake: Subsystem {
    lateinit var leftServo: Servo
    lateinit var rightServo: Servo

    @JvmField
    var leftName = "intake_left"
    @JvmField
    var rightName = "intake_right"

    val start: Command
        get() = ParallelGroup(
            ServoToPosition(leftServo, 1.0, this),
            ServoToPosition(rightServo, 1.0, setOf())
        )

    val reverse: Command
        get() = ParallelGroup(
            ServoToPosition(leftServo, 0.0, this),
            ServoToPosition(rightServo, 0.0,  setOf())
        )

    val stop: Command
        get() = ParallelGroup(
            ServoToPosition(leftServo, 0.5, this),
            ServoToPosition(rightServo, 0.5, setOf())
        )

    val slowForward: Command
        get() = ParallelGroup(
            ServoToPosition(leftServo, 0.55, this),
            ServoToPosition(rightServo, 0.55, setOf())
        )

    override fun initialize() {
        leftServo = OpModeData.hardwareMap.get(Servo::class.java, leftName)
        rightServo = OpModeData.hardwareMap.get(Servo::class.java, rightName)
        rightServo.direction = Servo.Direction.REVERSE
    }
}