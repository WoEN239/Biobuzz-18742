package org.firstinspires.ftc.teamcode.common.parts;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.blaze.Actuators.SmartMotor;
import org.firstinspires.ftc.teamcode.blaze.Anotations.AutowireActuator;
import org.firstinspires.ftc.teamcode.blaze.Anotations.AutowireCustom;
import org.firstinspires.ftc.teamcode.blaze.Anotations.BlazeModule;
import org.firstinspires.ftc.teamcode.blaze.Anotations.Command;
import org.firstinspires.ftc.teamcode.blaze.Anotations.InitWithTelemetry;
import org.firstinspires.ftc.teamcode.blaze.ModuleSubsystem.BlazingModule;

@InitWithTelemetry
@BlazeModule
@Configurable
public class DriveTrain extends BlazingModule {
    @AutowireActuator()
    SmartMotor lf;
    @AutowireActuator()
    SmartMotor lr;
    @AutowireActuator()
    SmartMotor rf;
    @AutowireActuator()
    SmartMotor rr;

    @AutowireCustom
    IMU imu;
    public static double maxPower = 1;

    public void fieldCentric(double forward, double strafe, double yaw){
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        double strafeRot = strafe * Math.cos(botHeading) - forward * Math.sin(botHeading);
        double forwardRot = strafe * Math.sin(botHeading) + forward * Math.cos(botHeading);
        moveRobot(forwardRot,strafeRot,yaw);
    }

    @Command
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

    @Override
    public void addTelemetry() {
        super.addTelemetry();
        telemetry.addData("rightLeft velocity",rr.getVelocity());
    }
}
