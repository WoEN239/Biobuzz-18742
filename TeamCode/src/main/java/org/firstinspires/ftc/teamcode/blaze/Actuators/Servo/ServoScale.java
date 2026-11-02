package org.firstinspires.ftc.teamcode.blaze.Actuators.Servo;

public class ServoScale {
    private double minScale;
    private double minRaw;
    private double maxScale;
    private double maxRaw;

    public ServoScale(double minScale, double minRaw, double maxScale, double maxRaw) {
        this.minScale = minScale;
        this.minRaw = minRaw;
        this.maxScale = maxScale;
        this.maxRaw = maxRaw;
    }

    public double minScale() {
        return minScale;
    }

    public void setMinScale(double minScale) {
        this.minScale = minScale;
    }

    public double minRaw() {
        return minRaw;
    }

    public void setMinRaw(double minRaw) {
        this.minRaw = minRaw;
    }

    public double maxScale() {
        return maxScale;
    }

    public void setMaxScale(double maxScale) {
        this.maxScale = maxScale;
    }

    public double maxRaw() {
        return maxRaw;
    }

    public void setMaxRaw(double maxRaw) {
        this.maxRaw = maxRaw;
    }
}
