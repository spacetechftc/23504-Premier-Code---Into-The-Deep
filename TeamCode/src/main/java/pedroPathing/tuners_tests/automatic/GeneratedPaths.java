package pedroPathing.tuners_tests.automatic;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.PathBuilder;


import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Config
@Autonomous(name = "PedroPathTest")
public class GeneratedPaths     {



    public static PathBuilder builder = new PathBuilder();

    public static PathChain line1 = builder
            .addPath(
                    new BezierCurve(
                            new Point(8.000, 80.000, Point.CARTESIAN),
                            new Point(21.533, 94.654, Point.CARTESIAN),
                            new Point(26.916, 76.262, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .build();

    public static PathChain line2 = builder
            .addPath(
                    new BezierCurve(
                            new Point(26.916, 76.262, Point.CARTESIAN),
                            new Point(29.832, 35.664, Point.CARTESIAN),
                            new Point(70.879, 24.897, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(
                    Math.toRadians(0),
                    Math.toRadians(0)
            )
            .build();

    public static PathChain line3 = builder
            .addPath(
                    new BezierCurve(
                            new Point(70.879, 24.897, Point.CARTESIAN),
                            new Point(128.075, 26.692, Point.CARTESIAN),
                            new Point(120.673, 72.673, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(
                    Math.toRadians(0),
                    Math.toRadians(0)
            )
            .build();

    public static PathChain line4 = builder
            .addPath(
                    new BezierCurve(
                            new Point(120.673, 72.673, Point.CARTESIAN),
                            new Point(106.318, 113.271, Point.CARTESIAN),
                            new Point(71.776, 120.224, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(
                    Math.toRadians(0),
                    Math.toRadians(0)
            )
            .build();

    public static PathChain line5 = builder
            .addPath(
                    new BezierCurve(
                            new Point(71.776, 120.224, Point.CARTESIAN),
                            new Point(39.028, 115.963, Point.CARTESIAN),
                            new Point(26.916, 76.486, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(
                    Math.toRadians(0),
                    Math.toRadians(0)
            )
            .build();
}
