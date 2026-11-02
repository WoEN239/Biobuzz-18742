package org.firstinspires.ftc.teamcode.teleOp;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.blaze.Alliance;
import org.firstinspires.ftc.teamcode.blaze.Anotations.AutowireModule;
import org.firstinspires.ftc.teamcode.blaze.BlazeCommon;
import org.firstinspires.ftc.teamcode.blaze.Controllers.PIDFController;
import org.firstinspires.ftc.teamcode.common.config.Constants;
import org.firstinspires.ftc.teamcode.common.parts.ActiveIntakeModule;
import org.firstinspires.ftc.teamcode.common.parts.DriveTrain;
import org.firstinspires.ftc.teamcode.common.parts.Kicker;
import org.firstinspires.ftc.teamcode.common.parts.Shooter;
import org.firstinspires.ftc.teamcode.common.parts.Transfer;
import org.firstinspires.ftc.teamcode.common.parts.Turret;

//@Config
public class TeleOpCommonV2 extends BlazeCommon {

    @AutowireModule
    ActiveIntakeModule intake;

    @AutowireModule
    DriveTrain driveTrain;

    @AutowireModule
    Shooter shooter;

    @AutowireModule
    Transfer transfer;

    @AutowireModule
    Kicker kicker;

    @AutowireModule
    Turret turret;

    Long loopTime = 0L;


    public TeleOpCommonV2(Alliance alliance) {
        super(alliance);
    }

    @Override
    public void init() {

        turret.setRotationSupplier(this::calculatePosition);

    }

    @Override
    public void update() {

        Long startTime = System.currentTimeMillis();
        super.update();
        driveTrain.moveRobot(gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_trigger - gamepad1.left_trigger );

        if(gamepad1.right_bumper){
            intake.suck();
            transfer.suck();
        } else if (gamepad1.left_bumper) {
            intake.outtake();
            if(gamepad1.x){
                transfer.unload();
            }
            else{
                transfer.stop();
            }

        } else {
            intake.stop();
            transfer.stop();
        }

        shooter.setRPM(400);
        turret.setPosition(Constants.position);
        if(gamepad1.squareWasPressed()){
            shooter.toggle();
            kicker.toggle();
        }

        loopTime = System.currentTimeMillis() - startTime;
    }

    @Override
    public void addTelemetry() {
        telemetry.addData("Loop time",loopTime);

        super.addTelemetry();
    }


    public double calculatePosition(){
        return 0;
    }
}
