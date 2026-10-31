package org.firstinspires.ftc.teamcode.teleOp;

import org.firstinspires.ftc.teamcode.blaze.Alliance;
import org.firstinspires.ftc.teamcode.blaze.Anotations.AutowireModule;
import org.firstinspires.ftc.teamcode.blaze.BlazeCommon;
import org.firstinspires.ftc.teamcode.common.parts.ActiveIntakeModule;
import org.firstinspires.ftc.teamcode.common.parts.DriveTrain;
import org.firstinspires.ftc.teamcode.common.parts.Kicker;
import org.firstinspires.ftc.teamcode.common.parts.Shooter;
import org.firstinspires.ftc.teamcode.common.parts.Transfer;
import org.firstinspires.ftc.teamcode.common.parts.Turret;

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


    public TeleOpCommonV2(Alliance alliance) {
        super(alliance);
    }

    @Override
    public void init() {
        shooter.setRpmSupplier(this::calculateRpm);
        turret.setRotationSupplier(this::calculatePosition);
    }

    @Override
    public void update() {
        super.update();
        driveTrain.moveRobot(gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.left_trigger - gamepad1.right_trigger );




        if (gamepad1.right_bumper) {
            transfer.suck();
            intake.suck();
        } else if (gamepad1.left_bumper) {
            intake.outtake();

        } else {
            intake.stop();
            if (!gamepad1.cross) {
                transfer.stop();
            }
        }

        if (gamepad1.cross && !gamepad1.right_bumper) {
            transfer.unload();
        }

        if (gamepad1.squareWasPressed()) {
            shooter.toggle();
            if(shooter.isOn()){
                shooter.openDoor();
                kicker.kick();
            }
            else{
                shooter.closeDoor();
                kicker.stop();
            }
        }
    }

    public double calculateRpm(){
        return 4000;
    }

    public double calculatePosition(){
        return 0;
    }
}
