package Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.utility.LambdaCommand;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

import Subsystems.Values.LiftPID;
import Subsystems.Values.RConstants;


public class Liftl extends Subsystem {
    // BOILERPLATE
    public static final Liftl INSTANCE = new Liftl();

    private Liftl() {}


    public MotorEx leftLift;
    public PIDFController l_liftController = new PIDFController(LiftPID.p, LiftPID.i, LiftPID.d, new StaticFeedforward(LiftPID.f));
    public String lname = "ml_l";


    public Command toTarget(double targetl) {
        return new RunToPosition(leftLift,
                targetl,
                l_liftController, this);

    }


    public Command HoldLift(){
        return new ParallelGroup(
                new HoldPosition(leftLift, l_liftController)
        );
    }


    @Override
    public void initialize() {
        leftLift = new MotorEx(lname);
        leftLift.resetEncoder();
        leftLift.setDirection(DcMotorSimple.Direction.REVERSE);
        l_liftController.setSetPointTolerance(LiftPID.tollerancel);


    }
}
