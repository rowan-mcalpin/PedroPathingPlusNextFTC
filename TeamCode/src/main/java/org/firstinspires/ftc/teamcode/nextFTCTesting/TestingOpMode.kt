package org.firstinspires.ftc.teamcode.nextFTCTesting

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode

@TeleOp(name = "NextFTC Testing", group = "testing")
class TestingOpMode: NextFTCOpMode(LinearSlide) {

    override fun onInit() {
        gamepadManager.gamepad1.a.pressedCommand = { LinearSlide.out }
        gamepadManager.gamepad1.b.pressedCommand = { LinearSlide.toIn }
    }
}