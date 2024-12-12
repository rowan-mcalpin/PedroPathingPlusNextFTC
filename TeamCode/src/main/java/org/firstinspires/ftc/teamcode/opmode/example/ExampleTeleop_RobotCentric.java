package org.firstinspires.ftc.teamcode.opmode.example;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.MathFunctions;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Vector;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.config.subsystem.ClawSubsystem;

/**
 * This is an example teleop that showcases movement and control of two servos and robot-centric driving.
 *
 * @author Baron Henderson - 20077 The Indubitables
 * @version 2.0, 11/28/2024
 */

@TeleOp(name = "Example Robot-Centric Teleop", group = "Examples")
public class ExampleTeleop_RobotCentric extends OpMode {
    private Follower follower;
    private ClawSubsystem claw;
    private final Pose startPose = new Pose(0,0,0);

    /** This method is call once when init is played, it initializes the follower and subsystems **/
    @Override
    public void init() {
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);

        claw = new ClawSubsystem(hardwareMap);
    }

    /** This method is called continuously after Init while waiting to be started. **/
    @Override
    public void init_loop() {
    }

    /** This method is called once at the start of the OpMode. **/
    @Override
    public void start() {
        follower.startTeleopDrive();
    }

    /** This is the main loop of the opmode and runs continuously after play **/
    @Override
    public void loop() {

        /* Update Pedro to move the robot based on:
        - Forward/Backward Movement: -gamepad1.left_stick_y
        - Left/Right Movement: -gamepad1.left_stick_x
        - Turn Left/Right Movement: -gamepad1.right_stick_x
        - Robot-Centric Mode: true
        */


        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        follower.update();

        /* Open claw on Left Bumper Press */
        if (gamepad1.left_bumper) {
            claw.openClaw();
        }

        /* Close claw on Right Bumper Press */
        if (gamepad1.right_bumper) {
            claw.closeClaw();
        }

        /* Ground Claw Pivot Position on A Press */
        if (gamepad1.a) {
            claw.groundClaw();
        }

        /* Scoring Claw Pivot Position on B Press */
        if (gamepad1.b) {
            claw.scoringClaw();
        }

        /* This could be paired with a PIDF to set the target position of the lift in teleop.
         * For this, you would have to update the lift pid and make sure to initializes the lift subsystem.
         */

        /*
        if (gamepad1.left_trigger > 0.5) {
            lift.setTarget(lTarget-50);
        }

        if (gamepad1.right_trigger > 0.5) {
            lift.setTarget(lTarget+50);
        }
        */

        /* Telemetry Outputs of our Follower */
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading in Degrees", Math.toDegrees(follower.getPose().getHeading()));

        /* Telemetry Outputs of our ClawSubsystem */
        telemetry.addData("Grab Position", claw.getGrabPosition());
        telemetry.addData("Pivot Position", claw.getPivotPosition());

        /* Update Telemetry to the Driver Hub */
        telemetry.update();

    }

    /** We do not use this because everything automatically should disable **/
    @Override
    public void stop() {
    }
}
