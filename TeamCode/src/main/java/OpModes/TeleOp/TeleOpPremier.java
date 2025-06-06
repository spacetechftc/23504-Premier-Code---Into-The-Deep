package OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import Subsystems.Extend;
import Subsystems.Intake;
import Subsystems.Lift;
import Subsystems.Outtake;

@TeleOp(name = "TeleOpPremier")
public class TeleOpPremier extends NextFTCOpMode {
    public TeleOpPremier(){
        super(Intake.INSTANCE, Lift.INSTANCE, Extend.INSTANCE, Outtake.INSTACE);
    }

    public String frontLeftName = "leftFront";
    public String frontRightName = "rightFront";
    public String backLeftName = "leftRear";
    public String backRightName = "rightRear";

    public MotorEx frontLeftMotor;
    public MotorEx frontRightMotor;
    public MotorEx backLeftMotor;
    public MotorEx backRightMotor;

    public MotorEx[] motors;

    public Command driverControlled;

    double outpower;
    double extendpower;


    @Override
    public void onInit() {
        frontLeftMotor = new MotorEx(frontLeftName);
        backLeftMotor = new MotorEx(backLeftName);
        backRightMotor = new MotorEx(backRightName);
        frontRightMotor = new MotorEx(frontRightName);

        // Change your motor directions to suit your robot.
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        motors = new MotorEx[] {frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor};


    }


    @Override
    public void onStartButtonPressed() {
        driverControlled = new MecanumDriverControlled(motors, gamepadManager.getGamepad1());
        driverControlled.invoke();


        Lift.INSTANCE.getDefaltCommand();

        gamepadManager.getGamepad2().getB().setPressedCommand(
                () -> Intake.INSTANCE.coletControl()
        );

        gamepadManager.getGamepad2().getRightTrigger().setPressedCommand(
                value -> Intake.INSTANCE.clawControl()
        );




        gamepadManager.getGamepad2().getY().setPressedCommand(
                () -> Extend.INSTANCE.powerControl(1)
        );
        gamepadManager.getGamepad2().getY().setReleasedCommand(
                () -> Extend.INSTANCE.powerControl(0)
        );

        gamepadManager.getGamepad2().getA().setPressedCommand(
                () -> Extend.INSTANCE.powerControl(-1)
        );

        gamepadManager.getGamepad2().getA().setReleasedCommand(
                () -> Extend.INSTANCE.powerControl(0)
        );



        gamepadManager.getGamepad2().getDpadUp().setPressedCommand(Lift.INSTANCE::toHigh);
        gamepadManager.getGamepad2().getDpadDown().setPressedCommand(Lift.INSTANCE::toLow);











    }

}
