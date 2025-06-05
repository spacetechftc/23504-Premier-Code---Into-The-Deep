package Subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class Outtake extends Subsystem {
    public static final Outtake INSTACE = new Outtake();
    private Outtake(){}

    public Servo ClawOut;
    public String cname = "clawOut";
    public boolean state;

    public Command closeClaw(){

        return new ServoToPosition(
                ClawOut, 0, this
        );
    }

    public Command openClaw(){
        return new ServoToPosition(
                ClawOut, 0, this
        );
    }




    @Override
    public void initialize(){
        ClawOut = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, cname);


    }


}
