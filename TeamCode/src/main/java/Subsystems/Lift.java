package Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

import Subsystems.Values.LiftPID;


public class Lift extends Subsystem {
    // BOILERPLATE
    public static final Lift INSTANCE = new Lift();

    private Lift() {}

    public MotorEx rightLift;
    public PIDFController r_liftController = new PIDFController(LiftPID.p, LiftPID.i, LiftPID.d, new StaticFeedforward(LiftPID.f));
    public String rname = "ml_r";
    public MotorEx leftLift;
    public PIDFController l_liftController = new PIDFController(LiftPID.p, LiftPID.i, LiftPID.d, new StaticFeedforward(LiftPID.f));
    public String lname = "ml_l";


    public Command toTarget(double targetl) {
        return new ParallelGroup(
                new RunToPosition(rightLift,
                        leftLift.getCurrentPosition(),
                        r_liftController
                ),
                new RunToPosition(leftLift,
                targetl,
                l_liftController
        )
        );

    }



    @Override
    public void initialize() {
        rightLift = new MotorEx(rname);
        rightLift.resetEncoder();
        rightLift.setDirection(DcMotorSimple.Direction.REVERSE);
        leftLift = new MotorEx(lname);
        leftLift.resetEncoder();
        leftLift.setDirection(DcMotorSimple.Direction.REVERSE);


    }
}
