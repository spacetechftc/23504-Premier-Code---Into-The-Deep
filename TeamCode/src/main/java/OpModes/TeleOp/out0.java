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
    Servo clawout;

    double arml_position;
    double armr_position;
    double handl_position;
    double handr_position;


    double armcontrol;
    double handcotrol;

    boolean fixe;


    double lastarml;
    double lastarmr;
    double lasthandl;
    double lasthandr;



    @Override
    public void init() {
        outarml = hardwareMap.get(Servo.class, "l_outarm");
        outarmr = hardwareMap.get(Servo.class, "r_outarm");
        outaxisl = hardwareMap.get(Servo.class, "l_outhand");
        outaxisr = hardwareMap.get(Servo.class, "r_outhand");
        clawout = hardwareMap.get(Servo.class, "clawOut");
        outarml.setDirection(Servo.Direction.FORWARD);
        outarmr.setDirection(Servo.Direction.REVERSE);
        outaxisr.setDirection(Servo.Direction.REVERSE);
        fixe = false;

    }

    @Override
    public void loop() {



        if(fixe == false) {
            armcontrol = gamepad2.left_stick_y;
            handcotrol = gamepad2.right_stick_y;
            armcontrol = ConvertRange(armcontrol, -1, 1, 0, 1);
            handcotrol = ConvertRange(handcotrol, -1, 1, 0, 1);

            outarml.setPosition(armcontrol);
            outarmr.setPosition(armcontrol);

            outaxisl.setPosition(handcotrol);
            outaxisr.setPosition(handcotrol);

            arml_position = outarml.getPosition();
            armr_position = outarmr.getPosition();
            handl_position = outaxisl.getPosition();
            handr_position = outaxisr.getPosition();
        } else {
            outarml.setPosition(lastarml);
            outarmr.setPosition(lastarmr);

            outaxisl.setPosition(lasthandl);
            outaxisr.setPosition(lasthandr);
        }

        if(gamepad2.right_bumper == true){
            if(fixe == false){
                fixe = true;
                lastarml = arml_position;
                lastarmr = armr_position;
                lasthandl = handl_position;
                lasthandr = handr_position;

            } else{
                fixe = false;
            }
        }

        telemetry.addData("arm l", arml_position);
        telemetry.addData("arm r", armr_position);
        telemetry.addData("hand l", handl_position);
        telemetry.addData("hand r", handr_position);


    }

    public double ConvertRange(double x, double in_min, double in_max, double out_min, double out_max){

        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;

    }
}
