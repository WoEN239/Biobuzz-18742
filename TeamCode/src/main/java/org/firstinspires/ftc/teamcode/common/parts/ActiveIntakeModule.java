package org.firstinspires.ftc.teamcode.common.parts;


import com.sun.tools.javac.code.Attribute;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.blaze.Actuators.Servo.SmartServo;
import org.firstinspires.ftc.teamcode.blaze.Actuators.SmartMotor;
import org.firstinspires.ftc.teamcode.blaze.Anotations.AutowireActuator;
import org.firstinspires.ftc.teamcode.blaze.Anotations.BlazeModule;
import org.firstinspires.ftc.teamcode.blaze.Anotations.InitWithTelemetry;
import org.firstinspires.ftc.teamcode.blaze.Anotations.InitWithoutTelemetry;
import org.firstinspires.ftc.teamcode.blaze.ModuleSubsystem.BlazingModule;
import org.firstinspires.ftc.teamcode.common.config.Constants;

@BlazeModule
@InitWithTelemetry
public class ActiveIntakeModule extends BlazingModule {


    private double intakePower = 1;

    @AutowireActuator
    SmartMotor intake;

    @AutowireActuator
    SmartServo mineplowServo;

    public void mineplowUp(){
        mineplowServo.setTarget(1);
    }

    public void mineplowDown(){
        mineplowServo.setTarget(0);
    }

    public void suck(){
        intake.setPower(intakePower);
    }

    public void outtake(){
        intake.setPower(-intakePower);
    }

    public void stop(){
        intake.setPower(0);
    }

    @Override
    public void addTelemetry() {
        super.addTelemetry();
        telemetry.addData("amps" , intake.getDcMotor().getCurrent(CurrentUnit.AMPS));
    }
}
