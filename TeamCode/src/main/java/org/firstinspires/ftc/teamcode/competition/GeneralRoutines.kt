package org.firstinspires.ftc.teamcode.competition

import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup
import com.rowanmcalpin.nextftc.core.command.utility.conditionals.PassiveSwitchCommand
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay
import org.firstinspires.ftc.teamcode.competition.subsystems.Arm
import org.firstinspires.ftc.teamcode.competition.subsystems.Claw
import org.firstinspires.ftc.teamcode.competition.subsystems.Intake
import org.firstinspires.ftc.teamcode.competition.subsystems.IntakeExtension
import org.firstinspires.ftc.teamcode.competition.subsystems.IntakePivot
import org.firstinspires.ftc.teamcode.competition.subsystems.IntakeSensor
import org.firstinspires.ftc.teamcode.competition.subsystems.Lift

object GeneralRoutines {

    lateinit var opModeColor: IntakeSensor.SampleColors
    lateinit var badColor: IntakeSensor.SampleColors

    val intakeFar: Command
        get() = ParallelGroup(
            IntakeExtension.toOut,
            IntakePivot.transfer,
            SequentialGroup(
                Delay(0.5),
                IntakePivot.down
            ),
            intakeTransferOrEject
        )

    val intakeClose: Command
        get() = ParallelGroup(
            IntakeExtension.toSlightlyOut,
            IntakePivot.transfer,
            SequentialGroup(
                Delay(0.5),
                IntakePivot.down
            ),
            intakeTransferOrEject
        )

    val intakeTransferOrEject: Command
        get() = SequentialGroup(
            Intake.start,
            IntakeSensor.waitUntilSample,
            PassiveSwitchCommand({ IntakeSensor.detectedColor },
                Pair(opModeColor, transfer),
                Pair(IntakeSensor.SampleColors.YELLOW, transfer),
                Pair(badColor, eject))
        )

    val transfer: Command
        get() = SequentialGroup(
            ParallelGroup(
                Intake.stop,
                IntakePivot.transfer,
                Lift.toSlightlyHigh,
                Claw.open,
                Arm.toIntake,
                IntakeExtension.toTransfer
            ),
            Lift.toIntake,
            Claw.close
        )

    val eject: Command
        get() = SequentialGroup(
            Intake.reverse,
            Delay(0.2),
            intakeTransferOrEject
        )
}