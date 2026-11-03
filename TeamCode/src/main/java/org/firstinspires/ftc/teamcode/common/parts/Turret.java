package org.firstinspires.ftc.teamcode.common.parts;



import org.firstinspires.ftc.teamcode.blaze.Actuators.Servo.SmartServo;
import org.firstinspires.ftc.teamcode.blaze.Actuators.SmartMotor;
import org.firstinspires.ftc.teamcode.blaze.Anotations.BlazeModule;
import org.firstinspires.ftc.teamcode.blaze.Anotations.InitWithTelemetry;
import org.firstinspires.ftc.teamcode.blaze.ModuleSubsystem.BlazingModule;
import org.firstinspires.ftc.teamcode.common.config.Constants;

import java.util.function.Supplier;

@BlazeModule
@InitWithTelemetry
public class Turret extends BlazingModule {

    Supplier<Double> rotationSupplier = null;

    boolean followSupplier = false;
    double positon;
    public void followSupplier(){
        followSupplier = true;
    }

    public void setRotationSupplier(Supplier<Double> rotationSupplier) {
        this.rotationSupplier = rotationSupplier;
    }

    public void setPosition(double position){
        powerTrain.setTarget(position);
        this.positon = position;
        followSupplier = false;
    }

    @Override
    public void update() {
        super.update();
        if(followSupplier){
            this.positon = rotationSupplier.get();
            powerTrain.setTarget(positon);
        }
    }

    @Override
    public void addTelemetry() {
        super.addTelemetry();
        telemetry.addData("position",powerTrain.get(SmartServo.class, Constants.turretServo1).getTarget());
    }
}
