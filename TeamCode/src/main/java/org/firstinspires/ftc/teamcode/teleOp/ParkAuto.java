package org.firstinspires.ftc.teamcode.teleOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.common.config.Constants;

@Autonomous
@Config
public class ParkAuto extends LinearOpMode {
    DcMotorEx  lf;
    DcMotorEx rf;
    DcMotorEx lr;
    DcMotorEx rr;
    double maxPower = 1;
    public static  double forwardTime = 500;
    public  static double shooterStartTime = 25000;
    @Override
    public void runOpMode() throws InterruptedException {
        lf = hardwareMap.get(DcMotorEx.class, Constants.frontLeftDrive);
        rf = hardwareMap.get(DcMotorEx.class,Constants.frontRightDrive);
        lr = hardwareMap.get(DcMotorEx.class,Constants.backLeftDrive);
        rr = hardwareMap.get(DcMotorEx.class,Constants.backRightDrive);
        lf.setDirection( DcMotorSimple.Direction.REVERSE);
        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        lr.setDirection(DcMotorSimple.Direction.REVERSE);
        rr.setDirection(DcMotorSimple.Direction.REVERSE);

        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        DcMotorEx shooter = hardwareMap.get(DcMotorEx.class,Constants.shooterMotor);
        shooter.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        long startTime = System.currentTimeMillis();
        while (opModeIsActive() && (System.currentTimeMillis() - startTime) < forwardTime){
            moveRobot(0.6,0,0);
        }

        while (opModeIsActive() && (System.currentTimeMillis() - startTime) < shooterStartTime){
            moveRobot(0,0,0);
        }

        while (opModeIsActive()){
            shooter.setPower(-1);
        }

    }

    public void moveRobot(double forward, double strafe, double yaw) {
        double frontLeftPower = forward + strafe + yaw;
        double backLeftPower = forward - strafe + yaw;
        double frontRightPower = forward - strafe - yaw;
        double backRightPower = forward + strafe - yaw;

        double max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        if (max > maxPower) {
            frontLeftPower /= max;
            frontRightPower /= max;
            backLeftPower /= max;
            backRightPower /= max;
        }


        lf.setPower(frontLeftPower * maxPower);
        rf.setPower(frontRightPower * maxPower);
        lr.setPower(backLeftPower * maxPower);
        rr.setPower(backRightPower * maxPower);
    }
}
