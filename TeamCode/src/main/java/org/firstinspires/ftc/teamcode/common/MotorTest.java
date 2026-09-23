package org.firstinspires.ftc.teamcode.common;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
@Configurable
public class MotorTest extends LinearOpMode {
    public static final String motorName1="motor";
    public static final String motorName2="";
    public static final boolean reverse1=false;
    public static final boolean reverse2=false;
    public static final double power = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx motor1=hardwareMap.get(DcMotorEx.class,motorName1);
        DcMotorEx motor2 = null ;
        if(!motorName2.isEmpty()){
             motor2=hardwareMap.get(DcMotorEx.class,motorName2);
        }

        motor1.setDirection(reverse1 ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
        if(motor2 !=null){
            motor2.setDirection(reverse2 ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
        }

        while (opModeIsActive()){
            motor1.setPower(power);
            if(motor2 != null){
                motor2.setPower(power);
            }

            motor1.setDirection(reverse1 ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
            if(motor2 !=null){
                motor2.setDirection(reverse2 ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
            }
        }

    }


}
