package Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.CommandManager;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower;

import Subsystems.Values.LiftPID;
import Subsystems.Values.RConstants;


public class Lift extends Subsystem {
    // BOILERPLATE
    public static final Lift INSTANCE = new Lift();

    private Lift() {}


    public MotorEx leftLift;
    public PIDFController l_liftController = new PIDFController(LiftPID.p, LiftPID.i, LiftPID.d, new StaticFeedforward(LiftPID.f));
    public String lname = "ml_l";
    public MotorEx rightLift;
    public PIDFController r_liftController = new PIDFController(LiftPID.p, LiftPID.i, LiftPID.d, new StaticFeedforward(LiftPID.f));
    public String rname = "ml_r";


    public Command toTarget(double targetl) {
        return new ParallelGroup(
                new RunToPosition(rightLift,
                        targetl,
                        r_liftController),
                new RunToPosition(leftLift,
                        targetl,
                        l_liftController)
        );


    }

    public Command toHigh() {
        return new ParallelGroup(
                new RunToPosition(rightLift,
                        RConstants.HIGHBASKET,
                        r_liftController),
                new RunToPosition(leftLift,
                        RConstants.HIGHBASKET,
                        l_liftController)
        );

    }
    public Command toLow() {
        return new ParallelGroup(
                new RunToPosition(rightLift,
                        RConstants.GROUND,
                        r_liftController),
                new RunToPosition(leftLift,
                        RConstants.GROUND,
                        l_liftController)
        );

    }

    public Command toSpecColet() {
        return new ParallelGroup(
                new RunToPosition(rightLift,
                        RConstants.SPECCOLET,
                        r_liftController),
                new RunToPosition(leftLift,
                        RConstants.SPECCOLET,
                        l_liftController)
        );

    }



    public Command powerControl(double power) {
        return new ParallelGroup(
                new SetPower(leftLift, power),
                new SetPower(rightLift, power)
        );

    }



    public Command getDefaltCommand(){
               return new ParallelGroup(
                       new HoldPosition(leftLift, l_liftController),
                       new HoldPosition(rightLift, r_liftController)
               );


    }


    @Override
    public void initialize() {
        leftLift = new MotorEx(lname);
        leftLift.resetEncoder();
        leftLift.setDirection(DcMotorSimple.Direction.REVERSE);
        l_liftController.setSetPointTolerance(LiftPID.tollerancel);
        rightLift = new MotorEx(rname);
        rightLift.resetEncoder();
        rightLift.setDirection(DcMotorSimple.Direction.FORWARD);
        r_liftController.setSetPointTolerance(LiftPID.tollerancer);
        CommandManager.INSTANCE.scheduleCommand(getDefaltCommand());


    }


}
