package OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class Diff extends OpMode {

    public Servo lDif;
    public Servo rDif;


    @Override
    public void init() {
        lDif  = hardwareMap.get(Servo.class, "lDiff");
        rDif = hardwareMap.get(Servo.class, "rDiff");
    }

    @Override
    public void loop() {
        lDif.setPosition(0);
        rDif.setPosition(0);
    }
}
