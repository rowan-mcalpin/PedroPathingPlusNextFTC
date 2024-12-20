package org.firstinspires.ftc.teamcode.competition.subsystems

import android.graphics.Color
import com.acmerobotics.dashboard.config.Config
import com.qualcomm.hardware.rev.RevColorSensorV3
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.utility.delays.WaitUntil
import com.rowanmcalpin.nextftc.ftc.OpModeData
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit

@Config
object IntakeSensor: Subsystem {
    
    lateinit var sensor: RevColorSensorV3
    
    @JvmField
    var sensorName = "intake_sensor"

    enum class SampleColors {
        YELLOW,
        BLUE,
        RED,
        NONE
    }
    
    var detectedColor: SampleColors = SampleColors.NONE
    
    private var hsv: FloatArray = FloatArray(3)
    
    val waitUntilSample: Command
        get() = WaitUntil {
            detectedColor != SampleColors.NONE
        }

    class Detect: Command() {
        override val isDone = false

        override fun update() {
            if (sensor.getDistance(DistanceUnit.CM) < 2) {
                Color.colorToHSV(sensor.normalizedColors.toColor(), hsv)
                detectedColor = if (hsv[0] <= 26) 
                    SampleColors.RED else if (hsv[0] <= 85) 
                        SampleColors.YELLOW else 
                            SampleColors.BLUE 
            } else {
                detectedColor = SampleColors.NONE
            }
        }
    }

    override fun initialize() {
        sensor = OpModeData.hardwareMap.get(RevColorSensorV3::class.java, sensorName)
        sensor.enableLed(true)
        
    }
}