package org.firstinspires.ftc.teamcode.common.parts;

import com.bylazar.configurables.annotations.Configurable;

import org.firstinspires.ftc.teamcode.blaze.Actuators.SmartMotor;
import org.firstinspires.ftc.teamcode.blaze.Actuators.SmartServo;
import org.firstinspires.ftc.teamcode.blaze.Anotations.AutowireActuator;
import org.firstinspires.ftc.teamcode.blaze.Anotations.BlazeModule;
import org.firstinspires.ftc.teamcode.blaze.Anotations.Command;
import org.firstinspires.ftc.teamcode.blaze.Anotations.InitWithTelemetry;
import org.firstinspires.ftc.teamcode.blaze.ModuleSubsystem.BlazingModule;

import java.util.function.Supplier;

@BlazeModule
@InitWithTelemetry
@Configurable
public class Shooter extends BlazingModule {

    @AutowireActuator
    SmartMotor shooterMotor;

    @AutowireActuator
    SmartServo shooterServo;

    @AutowireActuator
    SmartServo shooterDoorServo;
    public static double closePosition = 0.5;
    public static double openPosition = 0;

    Supplier<Double> rpmSupplier = null;

    boolean isOn = false;

    @Command
    public void setRPM(double rpm){
        if(rpmSupplier == null) return;
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
        }
        else{
            turnOn();
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
        super.update();
        if(isOn){
            shooterMotor.setTarget(rpmSupplier.get());
        }
    }

    @Override
    public void addTelemetry() {
        super.addTelemetry();
        telemetry.addData("Power",shooterMotor.getPower());
        telemetry.addData("Target",shooterMotor.getTarget());
    }
}
