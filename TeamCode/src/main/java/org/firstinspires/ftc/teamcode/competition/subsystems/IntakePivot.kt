package org.firstinspires.ftc.teamcode.competition.subsystems

import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup

object IntakePivot: Subsystem {
    val down: Command
        get() = SequentialGroup()

    val transfer: Command
        get() = SequentialGroup()
}