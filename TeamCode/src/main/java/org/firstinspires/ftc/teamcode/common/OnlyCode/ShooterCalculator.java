package org.firstinspires.ftc.teamcode.common.OnlyCode;

import com.acmerobotics.dashboard.config.Config;
@Config
public class ShooterCalculator {
    public static double wheelDiameter = 0.075;//0.1016 actual
    public static double slipFactor = 0.15;
    private final double g = 9.81;

    public static double thetaMin = 67;
    public static  double thetaMax = 67;
    public static double dTheta =0.01;
    public static double maxMotorRPM = 5400;
    public static double GoalHeight = 1.6;

    public ShooterCalculator(){

    }

    public double convertInchToMeter(double inch){
        return inch * 0.0254;
    }

    public static double speedToRPM(double speed_m_s) {
        return( ( speed_m_s) / (Math.PI * wheelDiameter)) * 60;
    }

    public static double rpmToSpeed(double rpm) {
        return (rpm * Math.PI * wheelDiameter) / 60.0;
    }

    public ShooterSolution calculateShooterParams(double distanceToWall){
        for (double theta = Math.toRadians(thetaMax); theta >=Math.toRadians(thetaMin); theta -= Math.toRadians(dTheta)) {
            double numerator =g * Math.pow(distanceToWall,2);
            double denominator = 2 * Math.pow(Math.cos(theta),2) * (distanceToWall *Math.tan(theta) - GoalHeight);
            if (denominator <= 0) continue;
            if (numerator <= 0) continue;

            double speed = Math.sqrt(numerator / denominator) ;

            double motorRPM =speedToRPM (speed)  * (1 + slipFactor);

            if (motorRPM <= maxMotorRPM) {
                return new ShooterSolution(motorRPM,Math.toDegrees(theta));
            }

        }
        return new ShooterSolution(maxMotorRPM,thetaMin);

    }




}
