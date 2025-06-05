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



    @Override
    public void onInit() {
        frontLeftMotor = new MotorEx(frontLeftName);
        backLeftMotor = new MotorEx(backLeftName);
        backRightMotor = new MotorEx(backRightName);
        frontRightMotor = new MotorEx(frontRightName);

        // Change your motor directions to suit your robot.
        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        motors = new MotorEx[] {frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor};


    }


    @Override
    public void onStartButtonPressed() {
        driverControlled = new MecanumDriverControlled(motors, gamepadManager.getGamepad1());
        driverControlled.invoke();

        if(Intake.INSTANCE.state == 0 || Intake.INSTANCE.state == 2) {
            gamepadManager.getGamepad2().getLeftBumper().setPressedCommand(
                    () -> new SequentialGroup(
                            Intake.INSTANCE.vertColet()
                    )
            );
        }  else if (Intake.INSTANCE.state == 1){
            gamepadManager.getGamepad2().getLeftBumper().setPressedCommand(
                    () -> new SequentialGroup(
                            Intake.INSTANCE.hoColet()
                    )
            );
        }


        gamepadManager.getGamepad2().getB().setPressedCommand(
                () -> new SequentialGroup(
                        Intake.INSTANCE.CloseClaw().and(Outtake.INSTACE.openClaw()),
                        Intake.INSTANCE.tranf().withDeadline(Extend.INSTANCE.retract()),
                        Outtake.INSTACE.closeClaw()
                )
        );

        if(Outtake.INSTACE.ClawOut.getPosition() < 1){
            gamepadManager.getGamepad2().getLeftBumper().setPressedCommand(
                    () -> new SequentialGroup(
                            Outtake.INSTACE.openClaw()
                    )
            );
        } else if (Outtake.INSTACE.ClawOut.getPosition() == 1){
            gamepadManager.getGamepad2().getLeftBumper().setPressedCommand(
                    () -> new SequentialGroup(
                            Outtake.INSTACE.closeClaw()
                    )
            );
        }


        Extend.INSTANCE.powerControl(gamepadManager.getGamepad2().getRightStick().getY());
        Lift.INSTANCE.powerControl(gamepadManager.getGamepad2().getLeftStick().getY());







    }
}
