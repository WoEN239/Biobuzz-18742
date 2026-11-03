package org.firstinspires.ftc.teamcode.common.parts;


import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.blaze.Actuators.SmartMotor;
import org.firstinspires.ftc.teamcode.blaze.Actuators.Servo.SmartServo;
import org.firstinspires.ftc.teamcode.blaze.Anotations.AutowireActuator;
import org.firstinspires.ftc.teamcode.blaze.Anotations.BlazeModule;
import org.firstinspires.ftc.teamcode.blaze.Anotations.Command;
import org.firstinspires.ftc.teamcode.blaze.Anotations.InitWithTelemetry;
import org.firstinspires.ftc.teamcode.blaze.Controllers.Controller;
import org.firstinspires.ftc.teamcode.blaze.ModuleSubsystem.BlazingModule;

import java.util.function.Supplier;

@BlazeModule
@InitWithTelemetry

public class Shooter extends BlazingModule {

    @AutowireActuator
    SmartMotor shooterMotor;

    @AutowireActuator
    SmartServo shooterServo;

    @AutowireActuator
    SmartServo shooterDoorServo;
    public static double closePosition = 0;
    public static double openPosition = 1;

    Supplier<Double> rpmSupplier = null;

    boolean isOn = false;

    @Command
    public void setRPM(double rpm){
        if(rpm != 0){
            isOn = true;
        }
        else{
            isOn = false;
        }
        shooterMotor.setTarget(rpm);
    }

    public void setRpmSupplier(Supplier<Double> rpmSupplier) {
        this.rpmSupplier = rpmSupplier;
    }

    @Command
    public void turnOff(){
        isOn = false;
        shooterMotor.setPower(0);
    }

    public void turnOn(){
        isOn = true;
    }

    @Command
    public void toggle(){
        if(isOn){
            turnOff();
            closeDoor();
        }
        else{
            turnOn();
            openDoor();
        }
    }

    @Command
    public void openDoor(){
        shooterDoorServo.setTarget(openPosition);
    }

    @Command
    public void closeDoor(){
        shooterDoorServo.setTarget(closePosition);
    }


    public boolean isOn(){
        return isOn;
    }

    @Override
    public void update() {
        shooterMotor.update();

    }

    public void setController(Controller controller){
        shooterMotor.setController(controller);
    }

    @Override
    public void addTelemetry() {
        super.addTelemetry();
        telemetry.addData("Power",shooterMotor.getPower());
        telemetry.addData("Amps",shooterMotor.getDcMotor().getCurrent(CurrentUnit.AMPS));
        telemetry.addData("Speed",shooterMotor.getTarget());
        telemetry.addData("Controller",shooterMotor.getController());
        telemetry.addData("Door target",shooterDoorServo.getTarget());
    }
}
