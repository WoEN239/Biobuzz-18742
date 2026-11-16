package org.firstinspires.ftc.teamcode.teleOp;

import org.firstinspires.ftc.teamcode.blaze.Alliance;
import org.firstinspires.ftc.teamcode.blaze.Anotations.AutowireModule;
import org.firstinspires.ftc.teamcode.blaze.BlazeCommon;
import org.firstinspires.ftc.teamcode.common.parts.ActiveIntakeModule;
import org.firstinspires.ftc.teamcode.common.parts.DriveTrain;
import org.firstinspires.ftc.teamcode.common.parts.Shooter;
import org.firstinspires.ftc.teamcode.common.parts.Transfer;

public class AutoBotCore extends BlazeCommon {
    @AutowireModule
    ActiveIntakeModule activeIntakeModule;
    @AutowireModule
    Shooter shooter;

    @AutowireModule
    Transfer transfer;
    long startTime;
    boolean isEnd = false;
    boolean isShoot = true;

    public static double shooterPosition = 57;
    public static double shooterTarget = 3500;
    public static double timeToSpin = 20000;

    public AutoBotCore(Alliance alliance) {
        super(alliance);
    }


    @Override
    public void onStart() {
        super.onStart();
         startTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        if(System.currentTimeMillis() - startTime < timeToSpin && !isEnd){
            isShoot = true;
        }

        if((System.currentTimeMillis() - startTime > timeToSpin) && !isEnd){
            isEnd = true;
            isShoot = false;
        }


        if(isShoot) {
            shooter.setRPM(-shooterTarget);
            shooter.setShooterPosition(shooterPosition);
            shooter.openDoor();
        }

        if(isEnd){
            transfer.suck();
            activeIntakeModule.suck();





        }

        super.update();
    }
}
