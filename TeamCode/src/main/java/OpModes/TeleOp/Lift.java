package OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import Subsystems.LiftSubSystem;
import Subsystems.Values.LiftPID;

@TeleOp
public class Lift extends OpMode {

    LiftSubSystem liftSubSystem = new LiftSubSystem(hardwareMap );

    double mp;

    @Override
    public void init() {

    }

    @Override
    public void loop() {

        if(gamepad1.a){
            liftSubSystem.RunToPosition(0);
        } else if(gamepad1.b) {
            liftSubSystem.RunToPosition(LiftPID.target);
        }else{
            mp = gamepad2.left_stick_y;
            liftSubSystem.PowerControl(mp);

        }


    }
}
