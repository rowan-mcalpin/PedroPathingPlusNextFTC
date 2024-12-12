package org.firstinspires.ftc.teamcode.config.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.config.RobotConstants;

/** This is a subsystem, for the claw of our robot
 * Here we make methods to manipulate the servos
 * We also import RobotConstants to get the positions of the servos.
 *
 * @author Baron Henderson - 20077 The Indubitables
 * @version 2.0, 9/8/2024
 */

public class ClawSubsystem {

    private Servo pivot, grab;

    /** This is the constructor for the subsystem, it maps the servos to the hardwareMap.
     * The device names should align with the configuration names on the driver hub.
     * To use this subsystem, we have to import this file, declare the subsystem (private ClawSubsystem claw;),
     * and then call the below constructor in the init() method. */

    public ClawSubsystem(HardwareMap hardwareMap) {
        pivot = hardwareMap.get(Servo.class, "pivot");
        grab = hardwareMap.get(Servo.class, "grab");
    }

    //------------------------------Grab------------------------------//

    /** This is the closeClaw method, it sets the grab to the closed position defined in RobotConstants. */
    public void closeClaw() {
        grab.setPosition(RobotConstants.closedClaw);
    }

    /** This is the openClaw method, it sets the grab to the open position defined in RobotConstants. */
    public void openClaw() {
        grab.setPosition(RobotConstants.openClaw);
    }

    //------------------------------Pivot------------------------------//

    /** This is the startClaw method, it sets the pivot to the start position defined in RobotConstants. */
    public void startClaw() {
        pivot.setPosition(RobotConstants.startClaw);
    }

    /** This is the groundClaw method, it sets the pivot to the ground position defined in RobotConstants. */
    public void groundClaw() {
        pivot.setPosition(RobotConstants.groundClaw);
    }

    /** This is the scoringClaw method, it sets the pivot to the scoring position defined in RobotConstants. */
    public void scoringClaw() {
        pivot.setPosition(RobotConstants.scoringClaw);
    }

    //------------------------------Getters------------------------------//

    /** This is the getGrabPosition method, it returns the current position of the grab. */
    public double getGrabPosition() {
        return grab.getPosition();
    }

    /** This is the getPivotPosition method, it returns the current position of the pivot. */
    public double getPivotPosition() {
        return pivot.getPosition();
    }

}