package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.blaze.Alliance;
import org.firstinspires.ftc.teamcode.blaze.ModuleSubsystem.BlazeTeleOp;

@TeleOp
public class TeleopV2 extends BlazeTeleOp<TeleOpCommonV2> {
    @Override
    public void createCore() {
        core=new TeleOpCommonV2(Alliance.BLUE);
    }
}
