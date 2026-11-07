package org.firstinspires.ftc.teamcode.common.config;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.blaze.Actuators.Actuator;
import org.firstinspires.ftc.teamcode.blaze.Actuators.Servo.SmartServo;
import org.firstinspires.ftc.teamcode.blaze.Actuators.SmartMotor;
import org.firstinspires.ftc.teamcode.blaze.Anotations.HardwareConfig;
import org.firstinspires.ftc.teamcode.blaze.AutoWire.ElectronicsConfig;
import org.firstinspires.ftc.teamcode.blaze.BlazeCore;
import org.firstinspires.ftc.teamcode.blaze.Controllers.PIDController;
import org.firstinspires.ftc.teamcode.blaze.Controllers.PIDFController;
import org.firstinspires.ftc.teamcode.blaze.ENCODER_RESOLUTION;
import org.firstinspires.ftc.teamcode.blaze.ModuleSubsystem.PowerTrainSubsystem.PowerTrain;
import org.firstinspires.ftc.teamcode.common.parts.ActiveIntakeModule;
import org.firstinspires.ftc.teamcode.common.parts.DriveTrain;
import org.firstinspires.ftc.teamcode.common.parts.Kicker;
import org.firstinspires.ftc.teamcode.common.parts.Shooter;
import org.firstinspires.ftc.teamcode.common.parts.Transfer;
import org.firstinspires.ftc.teamcode.common.parts.Turret;

@HardwareConfig
@Config
public class FullHardwareConfig extends ElectronicsConfig {
    public FullHardwareConfig(){}

    public static PIDFController shooterController = new PIDFController(
         new PIDFCoefficients(0.0019,0,0.000012,0.00025)
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


        PowerTrain intakePowerTrain = new PowerTrain.builder(hardwareMap,Constants.activeIntakePowerTrain)
                .addMotor(Constants.activeIntakeMotor, DcMotorSimple.Direction.FORWARD)
                .autowireTo(ActiveIntakeModule.class).build();

        PowerTrain driveTrain = new PowerTrain.builder(hardwareMap,Constants.driveTrainPowerTrain)
                .addMotor(Constants.backLeftDrive, DcMotorSimple.Direction.FORWARD)
                .addMotor(Constants.frontRightDrive, DcMotorSimple.Direction.FORWARD)
                .addMotor(Constants.backRightDrive, DcMotorSimple.Direction.FORWARD)
                .addMotor(Constants.frontLeftDrive, DcMotorSimple.Direction.FORWARD)
                .autowireTo(DriveTrain.class).build();

        SmartMotor shooterMotor = new SmartMotor.builder(hardwareMap,Constants.shooterMotor)
                .controller(shooterController)
                .direction(DcMotorSimple.Direction.FORWARD)
                .ticksToDegrees(12.85714285714286)
                .targetMode(Actuator.TargetMode.VELOCITY)
                .zeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT)
                .encoderReversed(false)//69,4000
                .build();

        PowerTrain shooterPowerTrain = new PowerTrain.builder(hardwareMap,Constants.shooterPowerTrain)
                .addActuator(shooterMotor)
                .addServo(Constants.shooterServo, Servo.Direction.FORWARD,60,0.4,90,0.8)
                .addActuator(new SmartServo.builder(hardwareMap,Constants.shooterDoorServo)
                        .setPwmRange(500,2500)
                        .setScale(0.35,0,0.78,1).build())
                .autowireTo(Shooter.class).build();

        PowerTrain transferPower = new PowerTrain.builder(hardwareMap,Constants.transferPowerTrain)
                .addMotor(Constants.transferMotor, DcMotorSimple.Direction.REVERSE)
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


        PowerTrain kickerPowerTrain = new PowerTrain.builder(hardwareMap,Constants.kickerPowerTrain)
                .addActuator(new SmartMotor.builder(hardwareMap,Constants.kickerMotor)
                        .zeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT)
                        .direction(DcMotorSimple.Direction.REVERSE).build())
                .autowireTo(Kicker.class).build();

        BlazeCore.addPowerTrains(intakePowerTrain,driveTrain,shooterPowerTrain,transferPower,turretPowerTrain,kickerPowerTrain);
    }
}
