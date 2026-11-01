package org.firstinspires.ftc.teamcode.common.config;


import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.blaze.Actuators.Actuator;
import org.firstinspires.ftc.teamcode.blaze.Actuators.SmartMotor;
import org.firstinspires.ftc.teamcode.blaze.Anotations.HardwareConfig;
import org.firstinspires.ftc.teamcode.blaze.AutoWire.ElectronicsConfig;
import org.firstinspires.ftc.teamcode.blaze.BlazeCore;
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
@Configurable
public class FullHardwareConfig extends ElectronicsConfig {
    public FullHardwareConfig(){}

    public static PIDFController shooterController = new PIDFController(
            new PIDFCoefficients(0.05,0.00001,0.00001,0.005)
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
                .direction(DcMotorSimple.Direction.REVERSE)
                .targetMode(Actuator.TargetMode.VELOCITY)
                .zeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT)
                .encoderReversed(false)
                .build();

        PowerTrain shooterPowerTrain = new PowerTrain.builder(hardwareMap,Constants.shooterPowerTrain)
                .addActuator(shooterMotor)
                .addServo(Constants.shooterServo, Servo.Direction.FORWARD,60,0,90,0.5)
                .addServo(Constants.shooterDoorServo, Servo.Direction.FORWARD)
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

        BlazeCore.addPowerTrains(intakePowerTrain,driveTrain,shooterPowerTrain,transferPower,turretPowerTrain,kickerPowerTrain);
    }
}
