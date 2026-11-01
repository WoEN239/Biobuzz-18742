package org.firstinspires.ftc.teamcode.blaze.ModuleSubsystem;



import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.blaze.MultiDashTelemetry;
import org.firstinspires.ftc.teamcode.blaze.TelemetryProvider;

import java.lang.annotation.Annotation;

public class EmptyTelemetry extends MultiDashTelemetry {


    public EmptyTelemetry(Telemetry coreTelemetry) {
        super(coreTelemetry);
    }

    @Override
            public void addData(String caption, Object value) {

            }

            @Override
            public void addGraph(String caption, Number value) {

            }

            @Override
            public void addLine(String message, Object value) {

            }

            @Override
            public void update() {

            }


}
