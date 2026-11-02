package org.firstinspires.ftc.teamcode.teleOp;

import android.content.Context;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.ftccommon.external.OnCreate;

@TeleOp
@Config
public class Teleop extends LinearOpMode {
    public static double sadasdas = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        while (opModeIsActive()){
//            FtcDashboard.getInstance().updateConfig();
            telemetry.addData("dssdsdsdss", sadasdas);
            telemetry.update();
        }
    }

//    @OnCreate
//    public static void start(Context context) {
//        FtcDashboard.start(context);
//    }
}
