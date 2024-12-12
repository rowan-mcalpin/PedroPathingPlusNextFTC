package org.firstinspires.ftc.teamcode.nextFTCTesting

import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode

class TestSubsystem: Subsystem {
    
}

class TestingOpMode: NextFTCOpMode(TestSubsystem(), TestSubsystem(), autoCreateGamepadManager = true) {
}