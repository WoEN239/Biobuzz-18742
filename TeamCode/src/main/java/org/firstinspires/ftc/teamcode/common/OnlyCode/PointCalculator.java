package org.firstinspires.ftc.teamcode.common.OnlyCode;


import com.pedropathing.math.Pose;


public  class PointCalculator {

    public static AzimuthDistance CalculateAzimuthAndDist(Pose botPose, Pose targetPose) {
        double dx = targetPose.x() - botPose.x(); // вперёд (+)
        double dy = targetPose.x() - botPose.y(); // вправо (+)

        double azimuth = Math.toDegrees(Math.atan2(dy, dx));

        botPose.minus(targetPose);


        double distance = Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));
        return new AzimuthDistance(azimuth,distance);
    }

}
