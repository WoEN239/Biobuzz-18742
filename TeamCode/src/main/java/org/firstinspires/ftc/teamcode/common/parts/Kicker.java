package org.firstinspires.ftc.teamcode.common.parts;


import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.blaze.Actuators.SmartMotor;
import org.firstinspires.ftc.teamcode.blaze.Anotations.AutowireActuator;
import org.firstinspires.ftc.teamcode.blaze.Anotations.BlazeModule;
import org.firstinspires.ftc.teamcode.blaze.Anotations.InitWithTelemetry;
import org.firstinspires.ftc.teamcode.blaze.ModuleSubsystem.BlazingModule;
import org.firstinspires.ftc.teamcode.common.config.Constants;


@InitWithTelemetry
@BlazeModule
public class Kicker extends BlazingModule {
    public static double kickPower = 1;
    boolean isKicking = false;

    @AutowireActuator
    SmartMotor kickerMotor;
    public void kick(){
        isKicking=true;
        kickerMotor.setPower(kickPower);
    }

    public void stop(){
        isKicking=false;
        kickerMotor.setPower(0);
    }

    public void toggle(){
        if(isKicking){
            stop();
        }
        else {
            kick();
        }
    }

    @Override
    public void addTelemetry() {
        super.addTelemetry();


        telemetry.addData("is kicking : " , isKicking ? "yes" : "no");
        telemetry.addData("Amps",kickerMotor.getDcMotor().getCurrent(CurrentUnit.AMPS));

    }
}
