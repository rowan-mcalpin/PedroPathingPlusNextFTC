package org.firstinspires.ftc.teamcode

val Double.inToMm get() = this * 25.4
val Double.mmToIn get() = this / 25.4
val Double.toRadians get() = (Math.toRadians(this))

val Int.inToMm get() = this * 25.4
val Int.mmToIn get() = this / 25.4