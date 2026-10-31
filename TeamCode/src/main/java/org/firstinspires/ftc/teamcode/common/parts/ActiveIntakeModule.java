package org.firstinspires.ftc.teamcode.common.parts;

import com.bylazar.configurables.annotations.Configurable;
import com.sun.tools.javac.code.Attribute;

import org.firstinspires.ftc.teamcode.blaze.Actuators.SmartMotor;
import org.firstinspires.ftc.teamcode.blaze.Anotations.AutowireActuator;
import org.firstinspires.ftc.teamcode.blaze.Anotations.BlazeModule;
import org.firstinspires.ftc.teamcode.blaze.Anotations.InitWithTelemetry;
import org.firstinspires.ftc.teamcode.blaze.ModuleSubsystem.BlazingModule;
import org.firstinspires.ftc.teamcode.common.config.Constants;

@BlazeModule
@InitWithTelemetry
@Configurable
public class ActiveIntakeModule extends BlazingModule {


    private double intakePower = 1;



    public void suck(){
        powerTrain.setPower(intakePower);
    }

    public void outtake(){
        powerTrain.setPower(-intakePower);
    }

    public void stop(){
        powerTrain.setPower(0);
    }

    @Override
    public void addTelemetry() {
        super.addTelemetry();
    }
}
