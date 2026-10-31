package org.firstinspires.ftc.teamcode.common.parts;

import com.bylazar.configurables.annotations.Configurable;
import org.firstinspires.ftc.teamcode.blaze.Anotations.BlazeModule;
import org.firstinspires.ftc.teamcode.blaze.Anotations.InitWithTelemetry;
import org.firstinspires.ftc.teamcode.blaze.ModuleSubsystem.BlazingModule;



@InitWithTelemetry
@BlazeModule
@Configurable
public class Kicker extends BlazingModule {
    public static double kickPower = 1;
    boolean isKicking = false;
    public void kick(){
        isKicking=true;
        powerTrain.setPower(kickPower);
    }

    public void stop(){
        isKicking=false;
        powerTrain.stopAll();
    }

    @Override
    public void addTelemetry() {
        super.addTelemetry();
        telemetry.addData("is kicking : " , isKicking ? "yes" : "no");
    }
}
