package Subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import Subsystems.Values.LiftPID;



public class LiftSubSystem  {

    DcMotorEx leftLift;
    DcMotorEx rightLift;
    PIDFController liftPid = new PIDFController(LiftPID.p, LiftPID.i, LiftPID.d, LiftPID.f);


    public LiftSubSystem(final HardwareMap hMap){
        rightLift = hMap.get(DcMotorEx.class, "LiftRight");
        leftLift = hMap.get(DcMotorEx.class, "LiftLeft");
    }


    public void RunToPosition(int target){
        while (!liftPid.atSetPoint()) {
            liftPid.setSetPoint(target);
            double output = liftPid.calculate(
                     leftLift.getCurrentPosition()
            );
            leftLift.setVelocity(output);
            rightLift.setVelocity(output);
        }
        Stop();
    }

    public void Stop() {
    leftLift.setPower(0);
    rightLift.setPower(0);
    }

    public void PowerControl(double power){
        leftLift.setPower(power);
        rightLift.setPower(power);
    }

}
