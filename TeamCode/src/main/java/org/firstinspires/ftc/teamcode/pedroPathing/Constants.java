package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("lf");
        c.frontRightName.set("rf");
        c.backLeftName.set("lr");
        c.backRightName.set("rr");
        c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });
    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-3.5681825172244097);
        c.yPodOffset.set(1.8545834098275253);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.4906817733565924);
                Controller secondaryTranslationalForward = Controller.proportional(0.18129380399680328);
                Controller primaryTranslationalLateral = Controller.proportional(0.6799390330199828);
                Controller secondaryTranslationalLateral = Controller.proportional(0.2512193043953109);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.015018640548299161));
                c.brake.set(Controller.proportionalFeedforward(0.012765844466054287));

                c.headingFeedback.set(Controller.proportional(7.4107983949288965));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.09149983117316428, 0.004297740717623458));

                c.linearBrakeCoefficients.set(Matrix.diag(0.10528252662445207, 0.06753945126673691));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0021541288816025947, 0.0021399138170292683));

                c.maxAchievableForwardVelocity.set(64.99233924883023);
                c.maxAchievableStrafeVelocity.set(45.99670830348275);
                c.naturalForwardDeceleration.set(32.37224566172283);
                c.naturalStrafeDeceleration.set(1350.8601982037442);
            }
    );

    public static Follower create(HardwareMap h) {

        return new Follower(new PinpointLocalizer(h,localizerConfig),new Mecanum(h,drivetrainConfig),new Foresight(foresightConfig));
    }
}