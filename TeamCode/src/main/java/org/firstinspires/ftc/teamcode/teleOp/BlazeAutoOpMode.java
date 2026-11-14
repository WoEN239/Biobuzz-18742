package org.firstinspires.ftc.teamcode.teleOp;

import org.firstinspires.ftc.teamcode.blaze.Alliance;

public class BlazeAutoOpMode extends org.firstinspires.ftc.teamcode.blaze.BlazeAutoOpMode<TeleOpCommonV2> {
    @Override
    public void setCore() {
        core = new TeleOpCommonV2(Alliance.BLUE);
        onNextStep("step");
    }




    @Override
    public void addActions() {

    }

    @Override
    public void buildPaths() {

    }
}
