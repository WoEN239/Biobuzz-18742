package org.firstinspires.ftc.teamcode.common.config;


import com.acmerobotics.dashboard.config.Config;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.blaze.Actuators.Actuator;
import org.firstinspires.ftc.teamcode.blaze.Actuators.Servo.SmartServo;
import org.firstinspires.ftc.teamcode.blaze.Actuators.SmartMotor;
import org.firstinspires.ftc.teamcode.blaze.Anotations.HardwareConfig;
import org.firstinspires.ftc.teamcode.blaze.AutoWire.ElectronicsConfig;
import org.firstinspires.ftc.teamcode.blaze.BlazeCore;
import org.firstinspires.ftc.teamcode.blaze.Controllers.PIDFController;
import org.firstinspires.ftc.teamcode.blaze.ModuleSubsystem.PowerTrainSubsystem.PowerTrain;
import org.firstinspires.ftc.teamcode.common.parts.ActiveIntakeModule;
import org.firstinspires.ftc.teamcode.common.parts.DriveTrain;
import org.firstinspires.ftc.teamcode.common.parts.Shooter;
import org.firstinspires.ftc.teamcode.common.parts.Transfer;
import org.firstinspires.ftc.teamcode.common.parts.Turret;

@HardwareConfig
@Config
public class FullHardwareConfig extends ElectronicsConfig {
    public static double range = 0.15;

    public FullHardwareConfig(){}

    public static PIDFController shooterController = new PIDFController(
         new PIDFCoefficients(0.004,0,0.0005,0.00019)
    );

    @Override
    protected void addElectronics(HardwareMap hardwareMap) {
        IMU imu = hardwareMap.get(IMU.class,"imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        ));

        imu.initialize(parameters);

        BlazeCore.addCustom("imu",imu);

        Follower follower = org.firstinspires.ftc.teamcode.pedroPathing.Constants.create(hardwareMap);
        BlazeCore.addCustom("follower",follower);


        PowerTrain intakePowerTrain = new PowerTrain.builder(hardwareMap,Constants.activeIntakePowerTrain)
                .addMotor(Constants.activeIntakeMotor, DcMotorSimple.Direction.FORWARD)
                .addServo("mineplowServo", Servo.Direction.REVERSE,0,0,1,0.58)
                .autowireTo(ActiveIntakeModule.class).build();

        PowerTrain driveTrain = new PowerTrain.builder(hardwareMap,Constants.driveTrainPowerTrain)
                .addMotor(Constants.backLeftDrive, DcMotorSimple.Direction.REVERSE)
                .addMotor(Constants.frontRightDrive, DcMotorSimple.Direction.REVERSE)
                .addMotor(Constants.backRightDrive, DcMotorSimple.Direction.REVERSE)
                .addMotor(Constants.frontLeftDrive, DcMotorSimple.Direction.REVERSE)
                .autowireTo(DriveTrain.class).build();

        SmartMotor shooterMotor = new SmartMotor.builder(hardwareMap,Constants.shooterMotor)
                .controller(shooterController)
                .direction(DcMotorSimple.Direction.REVERSE)
                .ticksToDegrees(12.85714285714286)
                .targetMode(Actuator.TargetMode.VELOCITY)
                .zeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT)
                .encoderReversed(true)//69,4000
                .build();
        //1 - 90
        //0.65- 55

        //mine plow min 0 max 0.58

        PowerTrain shooterPowerTrain = new PowerTrain.builder(hardwareMap,Constants.shooterPowerTrain)
                .addActuator(shooterMotor)
                .addServo(Constants.shooterServo, Servo.Direction.REVERSE,55,0.65,90,1)
                .addActuator(new SmartServo.builder(hardwareMap,Constants.shooterDoorServo)
                        .setPwmRange(500,2500)
                        .setScale(range,0,0.38,1).build())
                .autowireTo(Shooter.class).build();

        PowerTrain transferPower = new PowerTrain.builder(hardwareMap,Constants.transferPowerTrain)
                .addMotor(Constants.transferMotor, DcMotorSimple.Direction.REVERSE)
                .addMotor(Constants.transferMotor + "2", DcMotorSimple.Direction.REVERSE)
                .autowireTo(Transfer.class).build();

        double maxTurretRangeScale = 360;

        SmartServo baseTurretServo = new SmartServo.builder(hardwareMap,Constants.turretServo1)
                .setPwmRange(500,2500)
                .setScale(0,0,1,maxTurretRangeScale)
                .build();


        PowerTrain turretPowerTrain = new PowerTrain.builder(hardwareMap,Constants.turretPowerTrain)
                .addActuator(baseTurretServo)
                .addActuator(new SmartServo.builder(hardwareMap,Constants.turretServo2)
                        .copy(baseTurretServo).build())
                .autowireTo(Turret.class)
                .build();


        BlazeCore.addPowerTrains(intakePowerTrain,driveTrain,shooterPowerTrain,transferPower,turretPowerTrain);
    }
}
