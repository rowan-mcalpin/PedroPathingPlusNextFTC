package com.rowanmcalpin.nextftc.ftc

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower

object OpModeData {
    lateinit var hardwareMap: HardwareMap
    
    var follower: Follower? = null
    
    var gamepad1: Gamepad? = null
    var gamepad2: Gamepad? = null
}