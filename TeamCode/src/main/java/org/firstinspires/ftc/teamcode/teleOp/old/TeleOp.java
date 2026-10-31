package org.firstinspires.ftc.teamcode.teleOp.old;

import org.firstinspires.ftc.teamcode.blaze.Alliance;
import org.firstinspires.ftc.teamcode.blaze.ModuleSubsystem.BlazeTeleOp;

@Deprecated
@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends BlazeTeleOp<TeleOpCommon>{

    @Override
    public void createCore() {
        core = new TeleOpCommon(Alliance.BLUE);
    }
}