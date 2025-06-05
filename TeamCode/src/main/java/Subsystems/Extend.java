package Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower;

import Subsystems.Values.ExtendPID;
import Subsystems.Values.LiftPID;
import Subsystems.Values.RConstants;

public class Extend extends Subsystem {
    public static final Extend INSTANCE =new Extend();

    private Extend(){}


    public MotorEx Extend;
    public PIDFController e_controller = new PIDFController(ExtendPID.p, ExtendPID.i, ExtendPID.d, new StaticFeedforward(ExtendPID.f));
    public String ename = "m_e";


    public Command powerControl(double power) {
        return new SetPower(Extend, power);

    }

    public Command retract() {
        return new RunToPosition(Extend,
            RConstants.EXTEND_RETRACT,
                e_controller);

    }

    @Override
    public void initialize(){
        Extend = new MotorEx(ename);
        Extend.resetEncoder();
        Extend.setDirection(DcMotorSimple.Direction.REVERSE);
        e_controller.setSetPointTolerance(ExtendPID.tollerance);
    }

}
