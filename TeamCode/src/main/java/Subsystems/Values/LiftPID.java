package Subsystems.Values;
import com.acmerobotics.dashboard.config.Config;

@Config

public class LiftPID {
    public static int target = 0;
    public static int tollerancel = 50;
    public static int tollerancer = 50;
    public static double p = 0.01;
    public static double i = 0;
    public static double d = 0;
    public static double f = 0.01;
}
