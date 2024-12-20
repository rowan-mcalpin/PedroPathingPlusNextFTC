package org.firstinspires.ftc.teamcode.nextFTCTesting.examplebucketautojava;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.utility.NullCommand;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import kotlin.collections.SetsKt;

public class PivotSubsystem extends Subsystem {
    
    private static PivotSubsystem instance;
    
    private double startPivotPos = 0.174;
    private double groundPivotPos = 0.835;
    private double scoringPivotPos = 0.25;
    
    private Servo pivot;
    
    private PivotSubsystem() {
        
    }
    
    public static PivotSubsystem getInstance() {
        if (instance == null) {
            instance = new PivotSubsystem();
        }
        
        return instance;
    }
    
    public Command startPivot() {
        return new ServoToPosition(pivot, startPivotPos, getInstance());
    }
    
    public Command groundPivot() {
        return new ServoToPosition(pivot, groundPivotPos, getInstance());
    }
    
    public Command scoringPivot() {
        return new ServoToPosition(pivot, scoringPivotPos, getInstance());
    }

    @Override
    public void initialize() {
        pivot = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, "pivot");
    }

    @Override
    public void periodic() {
        
    }

    @NonNull
    @Override
    public Command getDefaultCommand() {
        return new NullCommand();
    }
}
