package org.firstinspires.ftc.teamcode.nextFTCTesting.examplebucketauto

import com.qualcomm.robotcore.hardware.Servo
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition

object PivotSubsystem: Subsystem {

    private lateinit var pivot: Servo
    
    var startPivotPos = 0.174
    var groundPivotPos = 0.835
    var scoringPivotPos = 0.25
    
    val startPivot: Command
        get() = ServoToPosition(pivot, startPivotPos, setOf(this@PivotSubsystem))
    val groundPivot: Command
        get() = ServoToPosition(pivot, groundPivotPos, setOf(this@PivotSubsystem))
    val scoringPivot: Command
        get() = ServoToPosition(pivot, scoringPivotPos, setOf(this@PivotSubsystem))

    override fun initialize() {
        pivot = OpModeData.hardwareMap.get(Servo::class.java, "pivot")
    }
}