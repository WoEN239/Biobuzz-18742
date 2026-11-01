package org.firstinspires.ftc.teamcode.common.parts;

import com.bylazar.configurables.annotations.Configurable;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.blaze.Actuators.SmartMotor;
import org.firstinspires.ftc.teamcode.blaze.Anotations.AutowireActuator;
import org.firstinspires.ftc.teamcode.blaze.Anotations.BlazeModule;
import org.firstinspires.ftc.teamcode.blaze.Anotations.InitWithTelemetry;
import org.firstinspires.ftc.teamcode.blaze.Anotations.InitWithoutTelemetry;
import org.firstinspires.ftc.teamcode.blaze.ModuleSubsystem.BlazingModule;
import org.firstinspires.ftc.teamcode.common.config.Constants;

@BlazeModule
@InitWithTelemetry
@Configurable
public class Transfer extends BlazingModule {


    @AutowireActuator
    SmartMotor transfer;

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

    @Override
    public void addTelemetry() {
        super.addTelemetry();
        telemetry.addData("amps",transfer.getDcMotor().getCurrent(CurrentUnit.AMPS));
    }
}
