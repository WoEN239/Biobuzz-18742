package org.firstinspires.ftc.teamcode.common;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@Configurable
public class ServoTest extends LinearOpMode {
    public static final String servoName1="motor";

    public static final boolean reverse1=false;
    public static final double pose = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        Servo motor1=hardwareMap.get(Servo.class,servoName1);
        motor1.setDirection(reverse1 ? Servo.Direction.REVERSE : Servo.Direction.FORWARD);


        while (opModeIsActive()){
            motor1.setPosition(pose);

        }

    }

}
