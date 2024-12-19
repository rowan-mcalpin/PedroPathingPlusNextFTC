package org.firstinspires.ftc.teamcode.competition.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode
import org.firstinspires.ftc.teamcode.competition.subsystems.Arm
import org.firstinspires.ftc.teamcode.competition.subsystems.Claw
import org.firstinspires.ftc.teamcode.competition.subsystems.IntakeExtension
import org.firstinspires.ftc.teamcode.competition.subsystems.IntakeSensor
import org.firstinspires.ftc.teamcode.competition.subsystems.Lift
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point
import org.firstinspires.ftc.teamcode.toRadians

@TeleOp(name = "Bucket Autonomous RED", group = "competition")
@Disabled
class BucketAuto: NextFTCOpMode(Lift, Arm, Claw, IntakeExtension, IntakeSensor) {
    
    val isRed = true
    
    override fun onInit() {
        follower = Follower(hardwareMap)
        follower.setStartingPose(startPose)

        buildPaths()

        Claw.close()
        Arm.toIntake()
        Lift.resetEncoders()
        Lift.toIntake()
        IntakeExtension.resetEncoders()
        IntakeExtension.toTransfer()
        IntakeSensor.Detect()()
    }

    override fun onUpdate() {
        // Data to Driver Hub
        telemetry.addData("x", follower.pose.x)
        telemetry.addData("y", follower.pose.y)
        telemetry.addData("heading", follower.pose.heading)
        telemetry.update()
    }
    
    //region PEDRO PATHING
    val startPose = Pose(9.0, 112.5, 0.0.toRadians)
    val scorePose = Pose(17.3, 126.6, (-45.0).toRadians)
    val sample1 = Pose(30.0, 120.0, 0.0.toRadians)
    val sample2 = Pose(30.0, 131.0, 0.0.toRadians)
    val sample3 = Pose(34.5, 130.0, 45.0.toRadians)
    val parkPose = Pose(60.0, 93.0, 90.0.toRadians)
    
    private lateinit var startToBucket: PathChain
    private lateinit var bucketToFirstSample: PathChain
    private lateinit var firstSampleToBucket: PathChain
    private lateinit var bucketToSecondSample: PathChain
    private lateinit var secondSampleToBucket: PathChain
    private lateinit var bucketToThirdSample: PathChain
    private lateinit var thirdSampleToBucket: PathChain
    private lateinit var bucketToPark: PathChain
    
    fun buildPaths() {
        startToBucket = follower.pathBuilder()
            .addPath(BezierCurve(
                Point(startPose),
                Point(23.971, 118.804, Point.CARTESIAN),
                Point(scorePose)
            ))
            .setLinearHeadingInterpolation(startPose.heading, scorePose.heading)
            .build()
        
        bucketToFirstSample = follower.pathBuilder()
            .addPath(BezierLine(
                Point(scorePose),
                Point(sample1)
            ))
            .setLinearHeadingInterpolation(scorePose.heading, sample1.heading)
            .build()
        
        firstSampleToBucket = follower.pathBuilder()
            .addPath(BezierLine(
                Point(sample1),
                Point(scorePose)
            ))
            .setLinearHeadingInterpolation(sample1.heading, scorePose.heading)
            .build()
        
        bucketToSecondSample = follower.pathBuilder()
            .addPath(BezierLine(
                Point(scorePose),
                Point(sample2)
            ))
            .setLinearHeadingInterpolation(scorePose.heading, sample2.heading)
            .build()

        secondSampleToBucket = follower.pathBuilder()
            .addPath(BezierLine(
                Point(sample2),
                Point(scorePose)
            ))
            .setLinearHeadingInterpolation(sample2.heading, scorePose.heading)
            .build()

        bucketToThirdSample = follower.pathBuilder()
            .addPath(BezierLine(
                Point(scorePose),
                Point(sample3)
            ))
            .setLinearHeadingInterpolation(scorePose.heading, sample3.heading)
            .build()

        thirdSampleToBucket = follower.pathBuilder()
            .addPath(BezierLine(
                Point(sample3),
                Point(scorePose)
            ))
            .setLinearHeadingInterpolation(sample3.heading, scorePose.heading)
            .build()

        bucketToPark = follower.pathBuilder()
            .addPath(BezierCurve(
                    Point(scorePose),
                    Point(63.689, 115.655, Point.CARTESIAN),
                    Point(58.790, 119.679, Point.CARTESIAN),
                    Point(parkPose)
            ))
            .setLinearHeadingInterpolation(scorePose.heading, parkPose.heading)
            .build()
    }
    //endregion
}