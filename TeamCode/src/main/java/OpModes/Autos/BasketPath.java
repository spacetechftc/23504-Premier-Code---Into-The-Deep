package OpModes.Autos;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.qualcomm.ftccommon.CommandList;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelDeadlineGroup;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.core.command.utility.delays.WaitUntil;
import com.rowanmcalpin.nextftc.core.units.Angle;
import com.rowanmcalpin.nextftc.core.units.TimeSpan;
import com.rowanmcalpin.nextftc.pedro.FollowPath;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;
import com.rowanmcalpin.nextftc.pedro.Turn;
import com.rowanmcalpin.nextftc.pedro.TurnTo;

import LIb.pedroPathing.constants.FConstants;
import LIb.pedroPathing.constants.LConstants;


import Subsystems.Extend;
import Subsystems.Intake;
import Subsystems.Lift;
import Subsystems.Outtake;
import Subsystems.Values.RConstants;

@Autonomous(name = "Basket Auto", group = "sides")
public class BasketPath extends PedroOpMode {

    public BasketPath() {
        super(Lift.INSTANCE, Extend.INSTANCE, Intake.INSTANCE, Outtake.INSTACE);
    }



    private final Pose startPose = new Pose(8.074766355140186, 112.14953271028037, Math.toRadians(0));  // Starting position
    private final Pose scorePose = new Pose(15, 126.953271028037386, Math.toRadians(-45));// Scoring position
    private final Pose scorePose1 = new Pose(16, 126.95, Math.toRadians(-45)) ;

    private final Pose pickup1Pose = new Pose(16, 124.5, Math.toRadians(0)); // First sample pickup
    private final Pose pickup2Pose = new Pose(19.3, 131.6, Math.toRadians(0)); // Second sample pickup
    private final Pose pickup3Pose = new Pose(33.19626168224299, 130.54205607476635, Math.toRadians(45)); // Third sample pickup

    private final Pose pickup1Submersive = new Pose(60, 98, Math.toRadians(270)); // Parking position
    private final Pose submersiveControlPose = new Pose(64.59813084112149, 102.2803738317757, Math.toRadians(0));


    private Path scorePreload, submersive;
    private PathChain  grabPickup1, grabPickup2, grabPickup3, scorePickup1, scorePickup2, scorePickup3, scoreSubmersive1;

    public void buildPaths() {
        /* This is our scorePreload path. We are using a BezierLine, which is a straight line. */
        scorePreload = new Path(new BezierLine(new Point(startPose), new Point(scorePose)));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading());

        /* Here is an example for Constant Interpolation
        scorePreload.setConstantInterpolation(startPose.getHeading()); */

        /* This is our grabPickup1 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        grabPickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup1Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup1Pose.getHeading())
                .build();

        /* This is our scorePickup1 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup1Pose), new Point(scorePose1)))
                .setLinearHeadingInterpolation(pickup1Pose.getHeading(), scorePose.getHeading())
                .build();

        /* This is our grabPickup2 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup2Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup2Pose.getHeading())
                .build();

        /* This is our scorePickup2 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup2Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup2Pose.getHeading(), scorePose.getHeading())
                .build();

        /* This is our grabPickup3 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        grabPickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup3Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup3Pose.getHeading())
                .build();

        /* This is our scorePickup3 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup3Pose), new Point(scorePose)))
                .setTangentHeadingInterpolation()
                .setReversed(true)
                .build();


        /* This is our park path. We are using a BezierCurve with 3 points, which is a curved line that is curved based off of the control point */
        submersive = new Path(new BezierCurve(new Point(scorePose), /* Control Point */ new Point(submersiveControlPose), new Point(pickup1Submersive)));
        submersive.setLinearHeadingInterpolation(scorePose.getHeading(), pickup1Submersive.getHeading());

        scoreSubmersive1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup1Submersive), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup1Submersive.getHeading(), scorePose.getHeading())
                .build();


    }

    public Command preload() {
        return new SequentialGroup(
                Outtake.INSTACE.closeClaw(),
                liftToScore(),
                score(),
                new ParallelGroup(
                        new FollowPath(grabPickup1),
                        Extend.INSTANCE.powerControl(1).endAfter(TimeSpan.fromSec(0.6))
                ),
                Extend.INSTANCE.powerControl(0),
                preco(),
                new FollowPath(scorePickup1).and(colet()),
                liftToScore(),
                score()


        );






    }

    public Command liftToScore(){
                return new SequentialGroup(
                        new ParallelGroup(
                                new FollowPath(scorePreload),
                                Lift.INSTANCE.toHigh()
                        ),
                        Outtake.INSTACE.bascketScore(),
                        new Delay(TimeSpan.fromSec(0.4))
                );



    }

    public Command score(){
        return new SequentialGroup(
                Outtake.INSTACE.openClaw(),
                new Delay(TimeSpan.fromSec(0.4)),
                Outtake.INSTACE.neutre(),
                Lift.INSTANCE.toLow()
        );
    }

    public Command preco(){
        return new SequentialGroup(
                Intake.INSTANCE.OpenClaw().and(Outtake.INSTACE.openClaw()),
                Intake.INSTANCE.hoColet(),
                new Delay(TimeSpan.fromSec(0.5)),
                Intake.INSTANCE.CloseClaw(),
                new Delay(TimeSpan.fromSec(0.4))
        );
    }

    public Command  colet(){
        return new SequentialGroup(
                Intake.INSTANCE.CloseClaw().and(Outtake.INSTACE.neutre()),
                new Delay(TimeSpan.fromSec(0.28)),
                Intake.INSTANCE.tranf().and(Extend.INSTANCE.powerControl(-1)),
                new Delay(TimeSpan.fromSec(0.8)),
                Extend.INSTANCE.powerControl(0),
                Outtake.INSTACE.colet(),
                new Delay(TimeSpan.fromSec(0.5)),
                Outtake.INSTACE.closeClaw(),
                new Delay(TimeSpan.fromSec(0.3)),
                Intake.INSTANCE.OpenClaw()
        );
    }





    /** This method is called once at the init of the OpMode. **/


    @Override
    public void onInit() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();

        new SequentialGroup(
                Outtake.INSTACE.neutre()
        );



    }

    @Override
    public void onStartButtonPressed() {
        super.onStartButtonPressed();
        Lift.INSTANCE.getDefaltCommand().invoke();
        preload().invoke();


    }


}
