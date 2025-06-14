package Subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class Outtake extends Subsystem {
    public static final Outtake INSTACE = new Outtake();
    private Outtake(){}

    public Servo ClawOut;
    public Servo l_outarm;
    public Servo r_outarm;
    public Servo l_outhand;
    public Servo r_outhand;

    public String cname = "clawOut";
    public String larm_name = "l_outarm";
    public String rarm_name = "r_outarm";
    public String lhand_name = "l_outhand";
    public String rhand_name = "l_outhand";


    public boolean state;

    public Command closeClaw(){

        return new ServoToPosition(
                ClawOut, 0, this
        );
    }

    public Command openClaw(){
        return new ServoToPosition(
                ClawOut, 1, this
        );
    }

    public Command bascketScore(){
        return new ParallelGroup(
                new ServoToPosition(l_outarm, 0, this),
                new ServoToPosition(r_outarm, 0 , this),
                new ServoToPosition(l_outhand, 0 , this),
                new ServoToPosition(r_outhand, 0 , this)
        );
    }


    @Override
    public void initialize(){
        ClawOut = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, cname);
        l_outarm = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, larm_name);
        r_outarm = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, rarm_name);
        l_outhand = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, lhand_name);
        r_outhand = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, rhand_name);


    }


}
