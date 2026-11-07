package org.firstinspires.ftc.teamcode.blaze.Actuators.Servo;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.blaze.Actuators.Actuator;
import org.firstinspires.ftc.teamcode.blaze.BlazeLogger;
import org.firstinspires.ftc.teamcode.blaze.Controllers.Controller;
import org.firstinspires.ftc.teamcode.blaze.Controllers.ServoController;

public class SmartServo implements Actuator {
    Servo servo;
    double tolerance;
    
    private ServoScale servoScale = new ServoScale(0,0,1,1);
    
    private PwmControl.PwmRange range;
    String name;

    private Controller controller = new ServoController();

    public SmartServo(Servo servo){
        this.servo = servo;
    }

    public SmartServo(HardwareMap hardwareMap,String name, Servo.Direction direction){
        setController(controller);
        long startTime = System.currentTimeMillis();
        servo = hardwareMap.get(Servo.class,name);
        this.name = name;
        servo.setDirection(direction);
        ServoImplEx servoImplEx = (ServoImplEx) servo;
        servoImplEx.setPwmRange(new PwmControl.PwmRange(500,2500));
        long timeToDo = System.currentTimeMillis() - startTime;
        BlazeLogger.addDefaultLog("SmartServo." + name,"Took " + timeToDo + " ms to full init");
    }


    private SmartServo(Servo servo,String name, Servo.Direction direction, PwmControl.PwmRange range){
        setController(controller);
        long startTime = System.currentTimeMillis();
        this.servo = servo;
        this.name = name;
        servo.setDirection(direction);
        ServoImplEx servoImplEx = (ServoImplEx) this.servo;
        if(range != null){
            this.range = range;
            servoImplEx.setPwmRange(range);
        }
        
        long timeToDo = System.currentTimeMillis() - startTime;
        BlazeLogger.addDefaultLog("SmartServo." + name,"Took " + timeToDo + " ms to full init");
    }

    @Override
    public void setTarget(double target) {
        if (target < servoScale.minScale()) target = servoScale.minScale();
        if (target > servoScale.maxScale()) target = servoScale.maxScale();


        double position = servoScale.minRaw() +
                (servoScale.maxRaw() - servoScale.minRaw()) *
                        (target - servoScale.minScale()) /
                        (servoScale.maxScale() - servoScale.minScale());

        //0.4 + 0.4 * 7 /

        servo.setPosition(position);
    }

    @Override
    public double getTarget() {
        double raw = servo.getPosition();

        if (servoScale.maxRaw() == servoScale.minRaw()) return servoScale.minScale();
        return servoScale.minScale()
                + (servoScale.maxScale() - servoScale.minScale())
                * (raw - servoScale.minRaw())
                / (servoScale.maxRaw() - servoScale.minRaw());
    }


    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    @Override
    public void reset() {}


    @Override
    public String getID() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return "SmartServo{" +
                "servo=" + servo.getDeviceName() +
                ", tolerance=" + tolerance +
                '}';
    }


    @Override
    public void scale(double from, double fromRaw, double to, double toRaw) {
        servoScale = new ServoScale(from,fromRaw,to,toRaw);
    }
    
    private void scale(ServoScale servoScale){
        this.servoScale = servoScale;
    }

    public static class builder{
        private final String name;
        private final Servo servo;
        
        private PwmControl.PwmRange range = null;
        private Servo.Direction direction = Servo.Direction.FORWARD;
        private ServoScale servoScale;
        
        public builder(HardwareMap hardwareMap,String name){
            this.name = name;
            servo = hardwareMap.get(Servo.class,name);
        }
        
        public builder setPwmRange(double min,double max){
            range = new PwmControl.PwmRange(min,max);
            return this;
        }
        
        public builder setDirection(Servo.Direction direction){
            this.direction = direction;
            return this;
        }
        
        public builder setScale(double minRaw,double minScale,double maxRaw,double maxScale){
            this.servoScale = new ServoScale(minScale,minRaw,maxScale,maxRaw);
            return this;
        }
        
        public builder copy(SmartServo smartServo){
            this.range = smartServo.range;
            this.servoScale = smartServo.servoScale;
            this.direction = smartServo.servo.getDirection();
            return this;
        }
        
        public SmartServo build(){
            SmartServo smartServo = new SmartServo(servo,name,direction,range);
            smartServo.scale(servoScale);
            return smartServo;
        }
    }
}
