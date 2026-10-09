package org.firstinspires.ftc.teamcode;

public class PIDFController {
    private double kP, kI, kD, kF;
    private double setpoint;
    private double integral;
    private double lastError;
    private double integralLimit = Double.POSITIVE_INFINITY;
    private double outputMin = -1.0;
    private double outputMax = 1.0;
    private long lastTime;
    private boolean first = true;

    public PIDFController(double kP, double kI, double kD, double kF) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
    }

    public void setCoefficients(double kP, double kI, double kD, double kF) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
    }

    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
    }

    public double getSetpoint() {
        return setpoint;
    }

    public void setIntegralLimit(double limit) {
        this.integralLimit = Math.abs(limit);
    }

    public void setOutputLimits(double min, double max) {
        this.outputMin = min;
        this.outputMax = max;
    }

    public double calculate(double measurement) {
        long now = System.nanoTime();
        double dt = first ? 0.0 : (now - lastTime) / 1e9;
        lastTime = now;

        double error = setpoint - measurement;

        if (dt > 0) {
            integral += error * dt;
            integral = Math.max(-integralLimit, Math.min(integralLimit, integral));
        }

        double derivative = (first || dt == 0) ? 0.0 : (error - lastError) / dt;
        lastError = error;
        first = false;

        double output = kP * error + kI * integral + kD * derivative + kF * setpoint;
        return Math.max(outputMin, Math.min(outputMax, output));
    }

    public double getError(double measurement) {
        return setpoint - measurement;
    }

    public boolean atSetpoint(double measurement, double tolerance) {
        return Math.abs(setpoint - measurement) <= tolerance;
    }

    public void reset() {
        integral = 0;
        lastError = 0;
        first = true;
    }
}
