package org.firstinspires.ftc.teamcode.teleOp;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Autonomous(group = "Test")
@Configurable
public class MultiDeviceTest extends LinearOpMode {
    public static String devices = "";

    public static String positions = "";

    List<Object> actuators = new ArrayList<>();//make an interface


    @Override
    public void runOpMode() throws InterruptedException {
        List<String> splitDevices = Arrays.asList(devices.split(" "));

        for(String device : splitDevices){
            String[] deviceAndType = device.split(":");
            String type = deviceAndType[0];
            String name = deviceAndType[1];
            String direction = deviceAndType[1];

            telemetry.addData("Write your actuator in format of TYPE:name:DIRECTION","");
            telemetry.addData("Where type can be MOTOR(m) or SERVO(s)","");
            telemetry.addData("Where name is the device name in config","");
            telemetry.addData("Where direction can be FORWARD(f) or REVERSE(r)","");
            telemetry.addData("devices should be separated by an space ","");
            telemetry.addData("Example:: SERVO:servo1:REVERSE m:motor1:f","");

            telemetry.addData("Then to set position/power you can write either 1 value: 0,5","");
            telemetry.addData("Or an array of values(size of devices) and separated by a space:0,5 1","");
            telemetry.update();
            telemetry.log();
            waitForStart();
            switch (type){
                case "MOTOR" :
                case "m": {
                    DcMotorEx motor = hardwareMap.get(DcMotorEx.class,name);
                    DcMotorSimple.Direction direction1 = null;
                    switch (direction){
                        case "r":
                        case "REVERSE":{
                            direction1 = DcMotorSimple.Direction.REVERSE;
                            break;
                        }
                        case "f":
                        case "FORWARD": {
                            direction1 = DcMotorSimple.Direction.FORWARD;
                            break;
                        }
                    }

                    motor.setDirection(direction1);
                    actuators.add(motor);
                    break;
                }
                case "s":
                case "SERVO":{
                    Servo servo = hardwareMap.get(Servo.class,name);
                    Servo.Direction direction1 = null;
                    switch (direction){
                        case "r":
                        case "REVERSE":{
                            direction1 = Servo.Direction.REVERSE;
                            break;
                        }
                        case "f":
                        case "FORWARD": {
                            direction1 = Servo.Direction.FORWARD;
                            break;
                        }
                    }

                    servo.setDirection(direction1);

                    actuators.add(servo);
                    break;
                }
            }
        }

        while (opModeIsActive()){
            List<String> positionsArray = Arrays.asList(positions.split(" "));
            if(positionsArray.size() == 1){
                double position = Double.parseDouble(positionsArray.get(0));
                for(Object actuator : actuators){
                    if(actuator instanceof DcMotorEx){
                        ((DcMotorEx) actuator).setPower(position);
                    }
                    if (actuator instanceof Servo){
                        ((Servo) actuator).setPosition(position);
                    }
                }
            }
            else if(positionsArray.size() == actuators.size()){

                for(Object actuator : actuators){
                    double position = Double.parseDouble(positionsArray.get(actuators.indexOf(actuator)));
                    if(actuator instanceof DcMotorEx){

                        ((DcMotorEx) actuator).setPower(position);
                    }
                    if (actuator instanceof Servo){
                        ((Servo) actuator).setPosition(position);
                    }
                }
            }
            else{
                telemetry.addData("Waiting for the right number of positions...","");
                for(Object actuator : actuators){
                    if(actuator instanceof DcMotorEx){
                        ((DcMotorEx) actuator).setPower(0);
                    }
                }
                telemetry.update();
            }

        }

    }
}
