package org.firstinspires.ftc.teamcode.common.parts;

import com.bylazar.configurables.annotations.Configurable;

import org.firstinspires.ftc.teamcode.blaze.Actuators.SmartMotor;
import org.firstinspires.ftc.teamcode.blaze.Anotations.AutowireActuator;
import org.firstinspires.ftc.teamcode.blaze.Anotations.BlazeModule;
import org.firstinspires.ftc.teamcode.blaze.Anotations.InitWithTelemetry;
import org.firstinspires.ftc.teamcode.blaze.ModuleSubsystem.BlazingModule;

@BlazeModule
@InitWithTelemetry
@Configurable
public class Transfer extends BlazingModule {



    double power = 1;

    public void suck(){
        powerTrain.setPower(power);
    }

    public void stop(){
        powerTrain.setPower(0);
    }

    public void unload(){
        powerTrain.setPower(-power);
    }
}
