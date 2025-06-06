package OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;

@TeleOp
public class tele extends OpMode {


    DcMotorEx extend;
    double power;

    @Override
    public void init() {
        extend = hardwareMap.get(DcMotorEx.class, "m_e");

    }

    @Override
    public void loop() {
        power = gamepad2.left_stick_y;
        extend.setPower(power);

    }
}
