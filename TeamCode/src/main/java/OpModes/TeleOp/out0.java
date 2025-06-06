package OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class out0 extends OpMode {

    Servo outarml;
    Servo outarmr;
    Servo outaxisl;
    Servo outaxisr;


    @Override
    public void init() {
        outarml = hardwareMap.get(Servo.class, "outarml");
        outarmr = hardwareMap.get(Servo.class, "outarmr");
        outaxisl = hardwareMap.get(Servo.class, "outaxisl");
        outaxisr = hardwareMap.get(Servo.class, "outaxisr");

    }

    @Override
    public void loop() {
        outaxisr.setPosition(0);
        outarml.setPosition(0);
        outarmr.setPosition(0);
        outaxisl.setPosition(0);

    }
}
