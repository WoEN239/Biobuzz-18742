package org.firstinspires.ftc.teamcode.blaze.Controllers;

public class ServoController implements Controller{
    @Override
    public double calculate(double target, double feedback) {
        return target;
    }

    @Override
    public double calculate(double error) {
        return error;
    }

    @Override
    public void setCoefficients(Object coef) {

    }

    @Override
    public double getI() {
        return 0;
    }

    @Override
    public void setI(double i) {

    }

    @Override
    public void setIMax(double v) {

    }

    @Override
    public double getControl() {
        return 0;
    }

    @Override
    public void reset() {

    }
}
