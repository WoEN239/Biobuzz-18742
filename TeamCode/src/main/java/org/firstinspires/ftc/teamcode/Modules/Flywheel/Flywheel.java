package org.firstinspires.ftc.teamcode.Modules.Flywheel;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.math.Pose;
import com.pedropathing.math.Vector;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


import org.firstinspires.ftc.teamcode.PIDFController;

import java.util.function.Supplier;


@Config
@Configurable
public class Flywheel {
    public static PIDFController pidfController = new PIDFController(0.01, 0, 0, 0.00039);

    public static boolean debug = false;


    public static double errorBorder = 0;

    FlywheelConstants flywheelConstants = new FlywheelConstants();

    public static double minDistNear = 57;
    public static double maxDistNear = 108;

    public static double minDistFar = 123;
    public static double maxDistFar = 161;

    public static double xGoal = -70;
    public static double yGoal = -70;

    public static double xGoalFar = -70;
    public static double yGoalFar = -68;


    private DcMotorEx shooter;
    private Servo servo;

    public static boolean usingK = false;

    public static double kT = 0;


    Supplier<Pose> pose;
    Supplier<Pose> vel;

    public void start(HardwareMap hardwareMap, Supplier<Pose> pose, Supplier<Pose> vel) {
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");

        servo = hardwareMap.get(Servo.class, "angle");

        this.vel = vel;
        this.pose = pose;


    }

    private double calculatePowerToDist(double dist2Tar, double minDist, double maxDist, double minVel2Tar, double maxVel2Tar) {
        double y = minVel2Tar + (dist2Tar - minDist) * (maxVel2Tar - minVel2Tar) / (maxDist - minDist);
        double realMin = Math.min(minVel2Tar, maxVel2Tar);
        double realMax = Math.max(minVel2Tar, maxVel2Tar);

        if (y > realMax) y = realMax;
        if (y < realMin) y = realMin;
        return y;
    }

    double velShooter = 0;

    double servoPose = 0;


    public static double vArtifact = 0.12;

    public void update() {


        Vector robotVel = vel.get().toVector2D().toVector();

        Pose goal;

        if (usingK) {
            goal = new Pose(Flywheel.xGoal + kT * robotVel.toVector2D().x(), Flywheel.yGoal + kT * robotVel.toVector2D().y(), 0);
        } else {
            goal = new Pose(Flywheel.xGoal, Flywheel.yGoal);
        }

        double distToTarget = pose.get().distance(goal);///add for future
        FtcDashboard.getInstance().getTelemetry().addData("goal + X", goal.x());
        FtcDashboard.getInstance().getTelemetry().addData("goal + Y", goal.y());


        if (distToTarget > 120) {
            Pose robotPose = pose.get();
            double dX = xGoal - robotPose.x();
            double dY = yGoal - robotPose.y();

            double absoluteAngleToGoal = Math.atan2(dY, dX);


            velShooter = calculatePowerToDist(distToTarget, minDistFar, maxDistFar, FlywheelConstants.nearRPMFromSide, FlywheelConstants.farRPMFromSide);

            servoPose = calculatePowerToDist(distToTarget, minDistFar, maxDistFar, FlywheelConstants.nearAngleFromSide, FlywheelConstants.farAngleFromSide);
        } else {

            velShooter = calculatePowerToDist(distToTarget, minDistNear, maxDistNear, FlywheelConstants.nearRPM, FlywheelConstants.farRPM);

            servoPose = calculatePowerToDist(distToTarget, minDistNear, maxDistNear, FlywheelConstants.nearAngle, FlywheelConstants.farAngle);

        }


        pidfController.setOutputLimits(0, 1);
        pidfController.setSetpoint(velShooter);
        double power = pidfController.calculate(shooter.getVelocity());

        shooter.setPower(power);

        servo.setPosition(servoPose);

        if (debug) {
            FtcDashboard dashboard = FtcDashboard.getInstance();
            TelemetryPacket packet = new TelemetryPacket();


            packet.put("Target Velocity", velShooter);


            packet.put("Current Velocity ", shooter.getVelocity());
            packet.put("distance", distToTarget);
            packet.put("far", distToTarget > 120);

            dashboard.sendTelemetryPacket(packet);

        }
    }


}