package org.firstinspires.ftc.teamcode.common.pedroPathing;

import com.pedropathing.tuning.autotune.Procedure;
import com.pedropathing.tuning.autotune.Tuner;

import org.firstinspires.ftc.teamcode.pedro.procedures.MecanumTuner;
import org.firstinspires.ftc.teamcode.pedro.procedures.PinpointTuner;

public class Tuning {
    @Tuner
    public static Procedure mecanunmTuner(){
        return new MecanumTuner();
    }

    @Tuner
    public static Procedure pinpoin(){
        return new PinpointTuner();
    }
}
