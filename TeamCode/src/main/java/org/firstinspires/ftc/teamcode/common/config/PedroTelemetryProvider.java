package org.firstinspires.ftc.teamcode.common.config;

import com.bylazar.graph.GraphManager;
import com.bylazar.graph.PanelsGraph;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.blaze.Anotations.TelemetryProvider;
import org.firstinspires.ftc.teamcode.blaze.MultiDashTelemetry;


@TelemetryProvider
public class PedroTelemetryProvider implements org.firstinspires.ftc.teamcode.blaze.TelemetryProvider {

    @Override
    public MultiDashTelemetry create(Telemetry coreTelemetry) {
        return new MultiDashTelemetry(coreTelemetry) {

            private final TelemetryManager panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();
            private final GraphManager graph = PanelsGraph.INSTANCE.getManager();

            @Override
            public void addData(String caption, Object value) {
                super.addData(caption, value);
                panelsTelemetry.addData(caption, value);
            }

            @Override
            public void addGraph(String caption, Number value) {
                panelsTelemetry.addData(caption, value);

                graph.addData(caption, value);
                graph.update();
            }

            @Override
            public void addLine(String message, Object value) {
                super.addLine(message, value);
                panelsTelemetry.addLine(message + " : " + value);
            }

            @Override
            public void update() {
                super.update();
                panelsTelemetry.update();
                graph.update();
            }
        };
    }
}