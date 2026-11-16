package org.firstinspires.ftc.teamcode.teleOp;


import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.blaze.Alliance;
import org.firstinspires.ftc.teamcode.blaze.Anotations.AutowireCustom;
import org.firstinspires.ftc.teamcode.blaze.Anotations.AutowireModule;
import org.firstinspires.ftc.teamcode.blaze.BlazeCommon;
import org.firstinspires.ftc.teamcode.blaze.Controllers.PIDFController;
import org.firstinspires.ftc.teamcode.common.OnlyCode.AzimuthDistance;
import org.firstinspires.ftc.teamcode.common.OnlyCode.PointCalculator;
import org.firstinspires.ftc.teamcode.common.parts.ActiveIntakeModule;
import org.firstinspires.ftc.teamcode.common.parts.DriveTrain;
import org.firstinspires.ftc.teamcode.common.parts.Shooter;
import org.firstinspires.ftc.teamcode.common.parts.Transfer;
import org.firstinspires.ftc.teamcode.common.parts.Turret;

@Config
public class TeleOpCommonV2 extends BlazeCommon {
    public static double position = 180;
    public static double shooterPosition = 57;
    public static double shooterTarget = 3500;
    public static Pose tagretPose = new Pose(0,0);
    public static Pose resetPose = new Pose(8,8);
    double azimuth = 0;
    double yaw = 0;
    double turning = 0;
    boolean shouldFollowTarget = false;

    public static PIDFController targetController = new PIDFController(
            new PIDFCoefficients(0,0,0,0)
    );
    boolean isCkining = false;
    @AutowireCustom(name = "follower")
    Follower follower;

    @AutowireCustom(name = "imu")
    IMU imu;



    @AutowireModule
    ActiveIntakeModule intake;

    @AutowireModule
    DriveTrain driveTrain;

    @AutowireModule
    Shooter shooter;

    @AutowireModule
    Transfer transfer;



    @AutowireModule
    Turret turret;

    Long loopTime = 0L;


    public TeleOpCommonV2(Alliance alliance) {
        super(alliance);
    }

    @Override

    public void init() {
        follower.setPose(new Pose(0,0,0));
        shooter.closeDoor();
    }

    @Override
    public void update() {

        long startTime = System.currentTimeMillis();
        super.update();
        driveTrain.moveRobot(gamepad1.left_stick_y,-gamepad1.left_stick_x,gamepad1.left_trigger - gamepad1.right_trigger );

        if(gamepad1.right_bumper){
            intake.suck();
            transfer.suck();
            if(gamepad1.triangle){
                intake.mineplowDown();
            }
            else{
                intake.mineplowUp();
            }
        } else if (gamepad1.left_bumper) {
            intake.mineplowUp();

            if(gamepad1.cross){
                transfer.unload();
            }
            else{
                transfer.stop();
            }
            intake.outtake();

        } else {
            intake.stop();
            transfer.stop();
            intake.mineplowUp();
        }

        shooter.setShooterPosition(shooterPosition);
        shooter.setRPM(-shooterTarget);
        turret.setPosition(position);
        if(gamepad1.squareWasPressed()){
            isCkining = !isCkining;
            shooter.toggle();
        }

        if (gamepad1.circleWasPressed()){
            shouldFollowTarget = !shouldFollowTarget;
        }

        if(shouldFollowTarget){
            AzimuthDistance ad = PointCalculator.CalculateAzimuthAndDist(follower.pose(),tagretPose);
            yaw =imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            turning = targetController.calculate(ad.getAzimuth(),yaw);
            driveTrain.moveRobot(gamepad1.left_stick_y,-gamepad1.left_stick_x,turning );
        }

        if(gamepad1.dpadUpWasPressed()){
            follower.setPose(resetPose);
        }


        loopTime = System.currentTimeMillis() - startTime;
    }

    public double getSpeed(){
        return shooterTarget;
    }

    @Override
    public void addTelemetry() {
        telemetry.addData("Loop time",loopTime);
        telemetry.addData("Position" , follower.pose());
        telemetry.addData("IS KICKING ",isCkining);
        telemetry.addData("azimuth",azimuth);
        telemetry.addData("yaw",yaw);
        telemetry.addData("Power to rotate",turning);
        super.addTelemetry();
    }


    public double calculatePosition(){
        return 0;
    }
}
