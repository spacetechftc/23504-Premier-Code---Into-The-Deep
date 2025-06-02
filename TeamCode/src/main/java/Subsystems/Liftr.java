package Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

import Subsystems.Values.LiftPID;


public class Liftr extends Subsystem {
    // BOILERPLATE
    public static final Liftr INSTANCE = new Liftr();

    private Liftr() {}

    public MotorEx rightLift;
    public PIDFController r_liftController = new PIDFController(LiftPID.p, LiftPID.i, LiftPID.d, new StaticFeedforward(LiftPID.f));
    public String rname = "ml_r";



    public Command toTarget(double targetl) {
        return new RunToPosition(rightLift,
                targetl,
                r_liftController, this);

    }


    public Command HoldLift(){
        return new ParallelGroup(
                new HoldPosition(rightLift, r_liftController)
        );
    }


    @Override
    public void initialize() {
        rightLift = new MotorEx(rname);
        rightLift.resetEncoder();
        rightLift.setDirection(DcMotorSimple.Direction.FORWARD);
        r_liftController.setSetPointTolerance(LiftPID.tollerancer);


    }
}
