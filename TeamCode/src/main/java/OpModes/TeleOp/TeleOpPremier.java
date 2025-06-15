package OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelDeadlineGroup;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.core.command.utility.delays.WaitUntil;
import com.rowanmcalpin.nextftc.core.units.TimeSpan;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import Subsystems.Extend;
import Subsystems.Intake;
import Subsystems.Lift;
import Subsystems.Outtake;
import Subsystems.Values.RConstants;
import Subsystems.Vision;

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
    double angle;

    public Vision vision;



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

        vision = new Vision(hardwareMap);
        vision.initializeCamera();
        vision.setLEDPWM();

        Intake.INSTANCE.getDefaltCommand();
        Outtake.INSTACE.colet();




    }


    @Override
    public void onStartButtonPressed() {
        driverControlled = new MecanumDriverControlled(motors, gamepadManager.getGamepad1());
        driverControlled.invoke();
        Lift.INSTANCE.getDefaltCommand().invoke();


        gamepadManager.getGamepad2().getRightTrigger().setPressedCommand(
                value -> Intake.INSTANCE.clawControl()
        );

        gamepadManager.getGamepad2().getB().setPressedCommand(
                () -> Intake.INSTANCE.intakeControl()
        );

        gamepadManager.getGamepad2().getRightTrigger().setPressedCommand(
                value -> Outtake.INSTACE.clawControl()
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


        gamepadManager.getGamepad2().getX().setPressedCommand(
                () -> new SequentialGroup(
                        Intake.INSTANCE.CloseClaw().and(Outtake.INSTACE.neutre()),
                        new Delay(TimeSpan.fromSec(0.28)),
                        Intake.INSTANCE.tranf().and(Extend.INSTANCE.powerControl(-1)),
                        new Delay(TimeSpan.fromSec(0.5)),
                        Extend.INSTANCE.powerControl(0),
                        Outtake.INSTACE.colet(),
                        new Delay(TimeSpan.fromSec(0.5)),
                        Outtake.INSTACE.closeClaw(),
                        new Delay(TimeSpan.fromSec(0.3)),
                        Intake.INSTANCE.OpenClaw()

                )
        );



        gamepadManager.getGamepad2().getDpadUp().setPressedCommand(
                () -> new SequentialGroup(
                        Lift.INSTANCE.toHigh(),
                        Outtake.INSTACE.bascketScore()
                ));
        gamepadManager.getGamepad2().getDpadDown().setPressedCommand(
                () -> new SequentialGroup(
                        Outtake.INSTACE.openClaw(),
                        new Delay(TimeSpan.fromSec(0.4)),
                        Outtake.INSTACE.neutre(),
                        Lift.INSTANCE.toLow()

                )

        );

        gamepadManager.getGamepad2().getRightBumper().setPressedCommand(() -> {
            vision.update();
            double grau = vision.getTurnServoDegree();
            telemetry.addData("angle", grau);
            telemetry.update();
            telemetry.update();
            if (grau < 10 || grau >130) {
                return Intake.INSTANCE.hoColet();
            } else {
                return Intake.INSTANCE.vertColet();
            }


        });







    }

}
