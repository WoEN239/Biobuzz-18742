package org.firstinspires.ftc.teamcode.common.config;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.blaze.Anotations.TelemetryProvider;
import org.firstinspires.ftc.teamcode.blaze.MultiDashTelemetry;


@TelemetryProvider
public class PedroTelemetryProvider implements org.firstinspires.ftc.teamcode.blaze.TelemetryProvider {

    @Override
    public MultiDashTelemetry create(Telemetry coreTelemetry) {
        return new MultiDashTelemetry(coreTelemetry) {



            @Override
            public void addData(String caption, Object value) {
                super.addData(caption, value);

            }

            @Override
            public void addGraph(String caption, Number value) {

            }

            @Override
            public void addLine(String message, Object value) {
                super.addLine(message, value);

            }

            @Override
            public void update() {
                super.update();

            }
        };
    }
}