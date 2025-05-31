package LIb.pedroPathing.tuners_tests.automatic;


import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;


public class Auto1 {

    public static PathBuilder builder = new PathBuilder();

    public static PathChain line1 = builder
            .addPath(
                    new BezierLine(
                            new Point(8.500, 112.100, Point.CARTESIAN),
                            new Point(19.300, 131.600, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-25))
            .setReversed(true)
            .build();

    public static PathChain line2 = builder
            .addPath(
                    new BezierLine(
                            new Point(19.300, 131.600, Point.CARTESIAN),
                            new Point(25.346, 127.626, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(-25))
            .build();

    public static PathChain line3 = builder
            .addPath(
                    new BezierLine(
                            new Point(25.346, 127.626, Point.CARTESIAN),
                            new Point(19.300, 131.600, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(-25))
            .setReversed(true)
            .build();

    public static PathChain line4 = builder
            .addPath(
                    new BezierLine(
                            new Point(19.300, 131.600, Point.CARTESIAN),
                            new Point(19.300, 131.600, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .build();

    public static PathChain line5 = builder
            .addPath(
                    new BezierLine(
                            new Point(19.300, 131.600, Point.CARTESIAN),
                            new Point(19.300, 131.600, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(-25))
            .build();

    public static PathChain line6 = builder
            .addPath(
                    new BezierLine(
                            new Point(19.300, 131.600, Point.CARTESIAN),
                            new Point(33.196, 130.093, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(45))
            .build();

    public static PathChain line7 = builder
            .addPath(
                    new BezierLine(
                            new Point(33.196, 130.093, Point.CARTESIAN),
                            new Point(19.300, 131.600, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(-25))
            .build();

    public static PathChain line8 = builder
            .addPath(
                    new BezierCurve(
                            new Point(19.300, 131.600, Point.CARTESIAN),
                            new Point(64.598, 102.280, Point.CARTESIAN),
                            new Point(62.579, 97.121, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(-25), Math.toRadians(270))
            .build();

    public static PathChain line9 = builder
            .addPath(
                    new BezierLine(
                            new Point(62.579, 97.121, Point.CARTESIAN),
                            new Point(19.065, 131.888, Point.CARTESIAN)
                    )
            )
            .setTangentHeadingInterpolation()
            .setReversed(true)
            .build();
}