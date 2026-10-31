package org.firstinspires.ftc.teamcode.teleOp.old;

import org.firstinspires.ftc.teamcode.blaze.Alliance;
import org.firstinspires.ftc.teamcode.blaze.Anotations.AutowireModule;
import org.firstinspires.ftc.teamcode.blaze.BlazeCommon;
import org.firstinspires.ftc.teamcode.common.parts.ActiveIntakeModule;
import org.firstinspires.ftc.teamcode.common.parts.DriveTrain;
import org.firstinspires.ftc.teamcode.common.parts.Shooter;
import org.firstinspires.ftc.teamcode.common.parts.Transfer;


@Deprecated
public class TeleOpCommon extends BlazeCommon {

    @AutowireModule
    ActiveIntakeModule intake;

    @AutowireModule
    DriveTrain driveTrain;

    @AutowireModule
    Shooter shooter;

    @AutowireModule
    Transfer transfer;


    /**
     * Constructs the common base, capturing current framework state and triggering autowiring.
     *
     * @param alliance The current alliance color for this OpMode run.
     */
    public TeleOpCommon(Alliance alliance) {
        super(alliance);
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
        }
    }

    @Override
    public void addTelemetry() {
        super.addTelemetry();
    }
}
