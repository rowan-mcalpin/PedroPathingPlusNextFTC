package org.firstinspires.ftc.teamcode.nextFTCTesting;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDController;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.coefficients.PIDCoefficients;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleMotorsToPosition;

import java.util.HashMap;

public class LinearSlideJava extends Subsystem {
    private static LinearSlideJava instance;
    
    private LinearSlideJava() { };
    
    public static LinearSlideJava getInstance() {
        if (instance == null) {
            instance = new LinearSlideJava();
        }
        
        return instance;
    }
    
    double inPos = 0.0;
    double outPos = 0.0;
    
    DcMotorEx motor1;
    DcMotorEx motor2;
    PIDFController controller1 = new PIDController(new PIDCoefficients(0.005, 0.0, 0.0));
    PIDFController controller2 = new PIDController(new PIDCoefficients(0.005, 0.0, 0.0));

    @Override
    public void initialize() {
        motor1 = OpModeData.INSTANCE.getHardwareMap().get(DcMotorEx.class, "motor1");
        motor2 = OpModeData.INSTANCE.getHardwareMap().get(DcMotorEx.class, "motor2");
        
        map.put(motor1, controller1);
        map.put(motor2, controller2);
    }
    
    private HashMap<DcMotorEx, PIDFController> map = new HashMap<>();
    
    public Command out() {
        return new MultipleMotorsToPosition(
                map,
                outPos,
                getInstance()
        );
    }
    
    public Command toIn() {
        return new MultipleMotorsToPosition(
                map,
                inPos,
                getInstance()
        );
    }
}
