package Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.gamepad.Button;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

import Subsystems.Values.RConstants;
import Utils.MathUtils;

public class Intake extends Subsystem {

    public static final Intake INSTANCE = new Intake();
    private Intake(){}

    public Servo lDiff;
    public Servo rDiff;
    public Servo clawIn;

    public String lname = "l_Diff";
    public String rname = "r_Diff";
    public String cname = "clawIn";

    public int state;

    public Command CloseClaw(){
        return new ServoToPosition(clawIn, // SERVO TO MOVE
                1, // POSITION TO MOVE TO
                this); //
    }

    public Command OpenClaw(){
        return new ServoToPosition(clawIn, // SERVO TO MOVE
                0, // POSITION TO MOVE TO
                this); //
    }

    public Command diffControl(double rotation, double inclination){
        double lAngle = inclination + (rotation/2);
        double rAngle = inclination - (rotation/2);

        lAngle = ConvertRange(lAngle, -1, 1, 0, 1);
        rAngle = ConvertRange(rAngle, -1, 1, 0, 1);


        return new ServoToPosition(lDiff, lAngle, this).asDeadline(new ServoToPosition(rDiff, rAngle, this));

    }


    public Command vertColet(){
        state = 1;
        return new ParallelGroup(
                new ServoToPosition(lDiff, RConstants.l_vertColet, this),
                new ServoToPosition(rDiff, RConstants.r_vertColet, this)
        );

    }

    public Command hoColet(){
        state = 2;
        return new ParallelGroup(
                new ServoToPosition(lDiff, RConstants.l_hoColet, this),
                new ServoToPosition(rDiff, RConstants.r_hoColet, this)
        );
    }

    public Command tranf(){
        state = 0;
        return new ParallelGroup(
                new ServoToPosition(lDiff, RConstants.l_transf, this),
                new ServoToPosition(rDiff, RConstants.r_tranf, this)
        );
    }




    @Override
    public void initialize(){
        lDiff = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, lname);
        rDiff = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, rname);
        clawIn = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, cname);
        lDiff.setDirection(Servo.Direction.FORWARD);
        rDiff.setDirection(Servo.Direction.REVERSE);


    }

    public double ConvertRange(double x, double in_min, double in_max, double out_min, double out_max){

        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;

    }

}
