package org.firstinspires.ftc.teamcode.nextFTCTesting.examplebucketautojava;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;
import com.rowanmcalpin.nextftc.ftc.driving.FollowPath;

import org.firstinspires.ftc.teamcode.nextFTCTesting.LinearSlideJava;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;

@Autonomous(name = "Example Bucket Auto Java")
@Disabled
public class ExampleBucketAuto extends NextFTCOpMode {
    // This function runs ONCE when the init button is pressed
    @Override
    public void onInit() {
        // Set up our follower
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
        
        // Build our paths
        buildPaths();
        
        // Since we can invoke a command to schedule it, let's do that here to set our claw and
        // pivot starting positions
        GrabSubsystem.getInstance().closeClaw().invoke();
        PivotSubsystem.getInstance().startPivot().invoke();
    }
    
    // This function runs ONCE when the start button is pressed
    @Override
    public void onStartButtonPressed() {
        // Once again, let's invoke a command to schedule it. This time, we're scheduling our main routine.
        autonomousRoutine().invoke();
    }
    
    // This function runs REPEATEDLY while the OpMode is running
    @Override
    public void onUpdate() {
        // Let's add data to telemetry every update
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }
    
    private Path path1;
    private Path path2;
    
    public Command routine() {
        return new ParallelGroup(
                new SequentialGroup(
                    new Delay(1.0), // Wait for 1 second
                    new FollowPath(path1)
                ),
                new SequentialGroup(
                    new Delay(3.0), // Wait for 3 seconds before moving the pivot
                    PivotSubsystem.getInstance().scoringPivot()
                ),
                LinearSlideJava.getInstance().out()
        );
    }
    
    public Command autonomousRoutine() {
        return new SequentialGroup(
                new FollowPath(scorePreload),
                new ParallelGroup(
                        GrabSubsystem.getInstance().openClaw(),
                        PivotSubsystem.getInstance().scoringPivot()
                ),
                new FollowPath(grabPickup1, true),
                new ParallelGroup(
                        GrabSubsystem.getInstance().closeClaw(),
                        PivotSubsystem.getInstance().groundPivot()
                ),
                new FollowPath(scorePickup1, true),
                new ParallelGroup(
                        GrabSubsystem.getInstance().openClaw(),
                        PivotSubsystem.getInstance().scoringPivot()
                ),
                new FollowPath(grabPickup2, true),
                new ParallelGroup(
                        GrabSubsystem.getInstance().closeClaw(),
                        PivotSubsystem.getInstance().groundPivot()
                ),
                new FollowPath(scorePickup2, true),
                new ParallelGroup(
                        GrabSubsystem.getInstance().openClaw(),
                        PivotSubsystem.getInstance().scoringPivot()
                ),
                new FollowPath(grabPickup3, true),
                new ParallelGroup(
                        GrabSubsystem.getInstance().closeClaw(),
                        PivotSubsystem.getInstance().groundPivot()
                ),
                new FollowPath(scorePickup3, true),
                new ParallelGroup(
                        GrabSubsystem.getInstance().openClaw(),
                        PivotSubsystem.getInstance().scoringPivot()
                ),
                new FollowPath(park, true),
                new ParallelGroup(
                        GrabSubsystem.getInstance().closeClaw(),
                        PivotSubsystem.getInstance().startPivot()
                )
        );
    }
    
    //region Paths
    private final Pose startPose = new Pose(9, 111, Math.toRadians(270));

    /** Scoring Pose of our robot. It is facing the submersible at a -45 degree (315 degree) angle. */
    private final Pose scorePose = new Pose(14, 129, Math.toRadians(315));

    /** Lowest (First) Sample from the Spike Mark */
    private final Pose pickup1Pose = new Pose(37, 121, Math.toRadians(0));

    /** Middle (Second) Sample from the Spike Mark */
    private final Pose pickup2Pose = new Pose(43, 130, Math.toRadians(0));

    /** Highest (Third) Sample from the Spike Mark */
    private final Pose pickup3Pose = new Pose(49, 135, Math.toRadians(0));

    /** Park Pose for our robot, after we do all of the scoring. */
    private final Pose parkPose = new Pose(60, 98, Math.toRadians(90));

    /** Park Control Pose for our robot, this is used to manipulate the bezier curve that we will create for the parking.
     * The Robot will not go to this pose, it is used a control point for our bezier curve. */
    private final Pose parkControlPose = new Pose(60, 98, Math.toRadians(90));

    /* These are our Paths and PathChains that we will define in buildPaths() */
    private Path scorePreload, park;
    private PathChain grabPickup1, grabPickup2, grabPickup3, scorePickup1, scorePickup2, scorePickup3;

    /** Build the paths for the auto (adds, for example, constant/linear headings while doing paths)
     * It is necessary to do this so that all the paths are built before the auto starts. **/
    public void buildPaths() {

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
        scorePreload = new Path(new BezierLine(new Point(startPose), new Point(scorePose)));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading());

        /* Here is an example for Constant Interpolation
        scorePreload.setConstantInterpolation(startPose.getHeading()); */

        /* This is our grabPickup1 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        grabPickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup1Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup1Pose.getHeading())
                .build();

        /* This is our scorePickup1 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup1Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup1Pose.getHeading(), scorePose.getHeading())
                .build();

        /* This is our grabPickup2 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup2Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup2Pose.getHeading())
                .build();

        /* This is our scorePickup2 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup2Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup2Pose.getHeading(), scorePose.getHeading())
                .build();

        /* This is our grabPickup3 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        grabPickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup3Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup3Pose.getHeading())
                .build();

        /* This is our scorePickup3 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup3Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup3Pose.getHeading(), scorePose.getHeading())
                .build();

        /* This is our park path. We are using a BezierCurve with 3 points, which is a curved line that is curved based off of the control point */
        park = new Path(new BezierCurve(new Point(scorePose), /* Control Point */ new Point(parkControlPose), new Point(parkPose)));
        park.setLinearHeadingInterpolation(scorePose.getHeading(), parkPose.getHeading());
    }
    //endregion
}
