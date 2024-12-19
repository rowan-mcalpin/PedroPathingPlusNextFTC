package org.firstinspires.ftc.teamcode.nextFTCTesting.examplebucketauto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode
import com.rowanmcalpin.nextftc.ftc.driving.FollowPath
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point

/**
 * 
 */
@Autonomous(name = "Example Bucket Auto")
@Disabled
class ExampleBucketAuto: NextFTCOpMode(GrabSubsystem, PivotSubsystem) {
    override fun onInit() {
        follower = Follower(hardwareMap)
        follower.setStartingPose(startPose)
        
        buildPaths()
        
        // Since we can invoke a command to schedule it, let's do that here to set our claw
        // and pivot starting positions
        GrabSubsystem.closeClaw()
        PivotSubsystem.startPivot()
    }

    override fun onStartButtonPressed() {
        autonomousRoutine()
    }

    override fun onUpdate() {
        // Data to Driver Hub
        telemetry.addData("x", follower.pose.x)
        telemetry.addData("y", follower.pose.y)
        telemetry.addData("heading", follower.pose.heading)
        telemetry.update()
    }
    
    //region Routines
    val autonomousRoutine: Command
        get() = SequentialGroup(
            FollowPath(scorePreload),
            ParallelGroup(
                GrabSubsystem.openClaw,
                PivotSubsystem.scoringPivot
            ),
            FollowPath(grabPickup1, true),
            ParallelGroup(
                GrabSubsystem.closeClaw,
                PivotSubsystem.groundPivot
            ),
            FollowPath(scorePickup1, true),
            ParallelGroup(
                GrabSubsystem.openClaw,
                PivotSubsystem.scoringPivot
            ),
            FollowPath(grabPickup2, true),
            ParallelGroup(
                GrabSubsystem.closeClaw,
                PivotSubsystem.groundPivot
            ),
            FollowPath(scorePickup2, true),
            ParallelGroup(
                GrabSubsystem.openClaw,
                PivotSubsystem.scoringPivot
            ),
            FollowPath(grabPickup3, true),
            ParallelGroup(
                GrabSubsystem.closeClaw,
                PivotSubsystem.groundPivot
            ),
            FollowPath(scorePickup3, true),
            ParallelGroup(
                GrabSubsystem.openClaw,
                PivotSubsystem.scoringPivot
            ),
            FollowPath(park, true),
            ParallelGroup(
                GrabSubsystem.closeClaw,
                PivotSubsystem.startPivot
            )
        )
    //endregion
    
    
    //region Path Creation
    private var startPose = Pose(9.0, 111.0, Math.toRadians(270.0))

    private var scorePose = Pose(14.0, 129.0, Math.toRadians(315.0))

    private var pickup1Pose = Pose(37.0, 121.0, Math.toRadians(0.0))

    private var pickup2Pose = Pose(43.0, 130.0, Math.toRadians(0.0))

    private var pickup3Pose = Pose(49.0, 135.0, Math.toRadians(0.0))

    private var parkPose = Pose(60.0, 98.0, Math.toRadians(90.0))

    private var parkControlPose = Pose(60.0, 98.0, Math.toRadians(90.0))

    private lateinit var scorePreload: Path
    private lateinit var park: Path
    
    private lateinit var grabPickup1: PathChain
    private lateinit var grabPickup2: PathChain
    private lateinit var grabPickup3: PathChain
    private lateinit var scorePickup1: PathChain
    private lateinit var scorePickup2: PathChain
    private lateinit var scorePickup3: PathChain

    /** Build the paths for the auto (adds, for example, constant/linear headings while doing paths)
     * It is necessary to do this so that all the paths are built before the auto starts.  */
    fun buildPaths() {
        /* There are two major types of paths components: BezierCurves and BezierLines.
                 *    * BezierCurves are curved, and require >= 3 points. There are the start and end points, and the control points.
                 *    - Control points manipulate the curve between the start and end points.
                 *    - A good visualizer for this is [this](https://pedro-path-generator.vercel.app/).
                 *    * BezierLines are straight, and require 2 points. There are the start and end points.
                 * Paths have can have heading interpolation: Constant, Linear, or Tangential
                 *    * Linear heading interpolation:
                 *    - Pedro will slowly change the heading of the robot from the startHeading to the endHeading over the course of the entire path.
                 *    * Constant Heading Interpolation:
                 *    - Pedro will maintain one heading throughout the entire path.
                 *    * Tangential Heading Interpolation:
                 *    - Pedro will follows the angle of the path such that the robot is always driving forward when it follows the path.
                 * PathChains hold Path(s) within it and are able to hold their end point, meaning that they will holdPoint until another path is followed.
                 * Here is a explanation of the difference between Paths and PathChains <https://pedropathing.com/commonissues/pathtopathchain.html> */

        /* This is our scorePreload path. We are using a BezierLine, which is a straight line. */

        scorePreload = Path(BezierLine(Point(startPose), Point(scorePose)))
        scorePreload.setLinearHeadingInterpolation(startPose.heading, scorePose.heading)

        /* Here is an example for Constant Interpolation
        scorePreload.setConstantInterpolation(startPose.getHeading()); */

        /* This is our grabPickup1 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        grabPickup1 = follower.pathBuilder()
            .addPath(BezierLine(Point(scorePose), Point(pickup1Pose)))
            .setLinearHeadingInterpolation(scorePose.heading, pickup1Pose.heading)
            .build()

        /* This is our scorePickup1 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup1 = follower.pathBuilder()
            .addPath(BezierLine(Point(pickup1Pose), Point(scorePose)))
            .setLinearHeadingInterpolation(pickup1Pose.heading, scorePose.heading)
            .build()

        /* This is our grabPickup2 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        grabPickup2 = follower.pathBuilder()
            .addPath(BezierLine(Point(scorePose), Point(pickup2Pose)))
            .setLinearHeadingInterpolation(scorePose.heading, pickup2Pose.heading)
            .build()

        /* This is our scorePickup2 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup2 = follower.pathBuilder()
            .addPath(BezierLine(Point(pickup2Pose), Point(scorePose)))
            .setLinearHeadingInterpolation(pickup2Pose.heading, scorePose.heading)
            .build()

        /* This is our grabPickup3 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        grabPickup3 = follower.pathBuilder()
            .addPath(BezierLine(Point(scorePose), Point(pickup3Pose)))
            .setLinearHeadingInterpolation(scorePose.heading, pickup3Pose.heading)
            .build()

        /* This is our scorePickup3 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup3 = follower.pathBuilder()
            .addPath(BezierLine(Point(pickup3Pose), Point(scorePose)))
            .setLinearHeadingInterpolation(pickup3Pose.heading, scorePose.heading)
            .build()

        /* This is our park path. We are using a BezierCurve with 3 points, which is a curved line that is curved based off of the control point */
        park = Path(
            BezierCurve(
                Point(scorePose),  /* Control Point */
                Point(parkControlPose), Point(parkPose)
            )
        )
        park.setLinearHeadingInterpolation(scorePose.heading, parkPose.heading)
    }
    //endregion

}