package org.firstinspires.ftc.teamcode.Modules.Flywheel;


import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.controllers.PIDController;

import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@Config
@Configurable
public class Flywheel {
    public static PIDFCoefficients flywheelMotorCoef = new PIDFCoefficients(0.01, 0, 0, 0.00039);
    
}
