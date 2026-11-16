package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.blaze.Alliance;
import org.firstinspires.ftc.teamcode.blaze.ModuleSubsystem.BlazeTeleOp;

@Autonomous
public class ShootFourAuto extends BlazeTeleOp<AutoBotCore> {
    @Override
    public void createCore() {
        core = new AutoBotCore(Alliance.BLUE);
    }
}
