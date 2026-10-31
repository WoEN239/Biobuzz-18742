package org.firstinspires.ftc.teamcode.common.config;


import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.blaze.Anotations.HardwareConfig;
import org.firstinspires.ftc.teamcode.blaze.AutoWire.ElectronicsConfig;
import org.firstinspires.ftc.teamcode.blaze.BlazeCore;
import org.firstinspires.ftc.teamcode.blaze.ModuleSubsystem.PowerTrainSubsystem.PowerTrain;
import org.firstinspires.ftc.teamcode.common.parts.ActiveIntakeModule;
import org.firstinspires.ftc.teamcode.common.parts.DriveTrain;
import org.firstinspires.ftc.teamcode.common.parts.Kicker;
import org.firstinspires.ftc.teamcode.common.parts.Shooter;
import org.firstinspires.ftc.teamcode.common.parts.Transfer;
import org.firstinspires.ftc.teamcode.common.parts.Turret;

@HardwareConfig
public class FullHardwareConfig extends ElectronicsConfig {
    public FullHardwareConfig(){}

    @Override
    protected void addElectronics(HardwareMap hardwareMap) {
        IMU imu = hardwareMap.get(IMU.class,"imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        ));

        imu.initialize(parameters);

        BlazeCore.addCustom("imu",imu);


        PowerTrain intakePowerTrain = new PowerTrain.builder(hardwareMap,Constants.activeIntakePowerTrain)
                .addMotor(Constants.activeIntakeMotor, DcMotorSimple.Direction.REVERSE)
                .autowireTo(ActiveIntakeModule.class).build();

        PowerTrain driveTrain = new PowerTrain.builder(hardwareMap,Constants.driveTrainPowerTrain)
                .addMotor(Constants.backLeftDrive, DcMotorSimple.Direction.FORWARD)
                .addMotor(Constants.frontRightDrive, DcMotorSimple.Direction.REVERSE)
                .addMotor(Constants.backRightDrive, DcMotorSimple.Direction.REVERSE)
                .addMotor(Constants.frontLeftDrive, DcMotorSimple.Direction.REVERSE)
                .autowireTo(DriveTrain.class).build();


        PowerTrain shooterPowerTrain = new PowerTrain.builder(hardwareMap,Constants.shooterPowerTrain)
                .addMotor(Constants.shooterMotor, DcMotorSimple.Direction.REVERSE)
                .addServo(Constants.shooterServo, Servo.Direction.FORWARD,60,0,90,0.5)
                .autowireTo(Shooter.class).build();

        PowerTrain transferPower = new PowerTrain.builder(hardwareMap,Constants.transferPowerTrain)
                .addMotor(Constants.transferMotor, DcMotorSimple.Direction.REVERSE)
                .autowireTo(Transfer.class).build();

        double maxTurretRangeScale = 180;


        PowerTrain turretPowerTrain = new PowerTrain.builder(hardwareMap,Constants.turretPowerTrain)
                .addServo(Constants.turretServo1, Servo.Direction.FORWARD,0,0.05,maxTurretRangeScale,0.95)
                .addServo(Constants.turretServo2, Servo.Direction.FORWARD,0,0.05,maxTurretRangeScale,0.95)
                .autowireTo(Turret.class)
                .build();

        PowerTrain kickerPowerTrain = new PowerTrain.builder(hardwareMap,Constants.kickerPowerTrain)
                .addMotor("kickerMotor", DcMotorSimple.Direction.REVERSE)
                .autowireTo(Kicker.class).build();

        BlazeCore.addPowerTrains(intakePowerTrain,driveTrain,shooterPowerTrain,transferPower,turretPowerTrain);
    }
}
