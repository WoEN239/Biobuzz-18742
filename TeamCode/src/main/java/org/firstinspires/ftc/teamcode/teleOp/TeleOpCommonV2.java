package org.firstinspires.ftc.teamcode.teleOp;


import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.blaze.Alliance;
import org.firstinspires.ftc.teamcode.blaze.Anotations.AutowireCustom;
import org.firstinspires.ftc.teamcode.blaze.Anotations.AutowireModule;
import org.firstinspires.ftc.teamcode.blaze.BlazeCommon;
import org.firstinspires.ftc.teamcode.common.parts.ActiveIntakeModule;
import org.firstinspires.ftc.teamcode.common.parts.DriveTrain;
import org.firstinspires.ftc.teamcode.common.parts.Shooter;
import org.firstinspires.ftc.teamcode.common.parts.Transfer;
import org.firstinspires.ftc.teamcode.common.parts.Turret;

@Config
public class TeleOpCommonV2 extends BlazeCommon {
    public static double position = 180;
    public static double shooterPosition = 60;
    public static double shooterTarget = 3750;
    @AutowireCustom(name = "follower")
    Follower follower;

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
        driveTrain.moveRobot(gamepad1.left_stick_y,-gamepad1.left_stick_x,gamepad1.right_trigger - gamepad1.left_trigger );

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
            if(gamepad1.x){
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
            shooter.toggle();
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
        super.addTelemetry();
    }


    public double calculatePosition(){
        return 0;
    }
}
