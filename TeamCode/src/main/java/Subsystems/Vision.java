package Subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import Utils.MathUtils;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Vision {


    public   Limelight3A camera;

    public Servo led;

    public static double ledPWM = 0.5;

    private boolean isDataOld = false;
    private SampleColor detectionColor = SampleColor.BLUE;
    private LLResult result;

    public static double CAMERA_HEIGHT = 307.0 - 16;
    public static double CAMERA_ANGLE = -45.0;
    public static double TARGET_HEIGHT = 19.05;

    public static double strafeConversionFactor = 6.6667;
    public static double cameraStrafeToBot = -20;

    public static double sampleToRobotDistance = 145;


    public Vision(final HardwareMap hardwareMap) {
        camera = hardwareMap.get(Limelight3A.class, "limelight");
        led = hardwareMap.get(Servo.class, "LED");
    }



    public void setLEDPWM() {
        led.setPosition(ledPWM);
    }

    public void initializeCamera() {
        camera.setPollRateHz(50);
        camera.start();
    }



    public enum SampleColor {
        RED(0.0),
        BLUE(1.0),
        YELLOW(2.0);

        private final double colorVal;

        SampleColor(double colorVal) {
            this.colorVal = colorVal;
        }
    }

    public double getTx(double defaultValue) {
        if (result == null) {
            return defaultValue;
        }
        return result.getTx();
    }

    public double getTy(double defaultValue) {
        if (result == null) {
            return defaultValue;
        }
        return result.getTy();
    }

    public boolean isTargetVisible() {
        if (result == null) {
            return false;
        }
        return !MathUtils.isNear(0, result.getTa(), 0.0001);
    }

    public double getDistance() {
        double ty = getTy(0.0);
        if (MathUtils.isNear(0, ty, 0.01)) {
            return 0;
        }
        double angleToGoalDegrees = CAMERA_ANGLE + ty;
        double angleToGoalRadians = Math.toRadians(angleToGoalDegrees);
        double distanceMM = (TARGET_HEIGHT - CAMERA_HEIGHT) / Math.tan(angleToGoalRadians);
        return Math.abs(distanceMM) - sampleToRobotDistance;
    }

    // Get the strafe
    public double getStrafeOffset() {
        double tx = getTx(0);
        if (tx != 0) {
            return tx * strafeConversionFactor - cameraStrafeToBot;
        }
        return 0;
    }

    public double getTurnServoDegree() {
        if (result == null) {
            return 0;
        }
        return result.getPythonOutput()[3];
    }


    public void update() {
        camera.updatePythonInputs(
                new double[] {detectionColor.colorVal, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0});
        result = camera.getLatestResult();

        if (result != null) {
            long staleness = result.getStaleness();
            // Less than 100 milliseconds old
            isDataOld = staleness >= 100;

            //      telemetry.addData("Tx", result.getTx());
            //      telemetry.addData("Ty", result.getTy());
            //      telemetry.addData("Ta", result.getTa());
            // telemetry.update();
        }
    }
}