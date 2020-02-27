package org.firstinspires.ftc.teamcode.managers;

import org.firstinspires.ftc.teamcode.autonomous.*;
import org.firstinspires.ftc.teamcode.auxilary.*;
import org.firstinspires.ftc.teamcode.managers.*;
import org.firstinspires.ftc.teamcode.teleop.*;

public class FeatureManager {

    public final static float SPEED = 0.6f;
    public final static int TICK_PER_ROT = 1680;

    public final static float P = 0.03f;
    public final static double ENCODER_CPR = 1680;
    public final static double GEAR_RATIO = 1;
    public final static double WHEEL_DIAMETER = 4;
    public final static double SLIP = 0.7;
    public final static double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    public final static float EXPONENTIAL_SCALAR = 3f;

    public final static int INPUT_DOUBLECLICK_TIME = 400;

    public static class ControlMap {
        public final static Control MOVE_HORIZONTAL = new Control("left_stick_x");
        public static final Control MOVE_VERTICAL = new Control("left_stick_y");
        public static final Control MOVE_ROTATIONAL = new Control("right_stick_x");
    }
}

    class Control {
        public String on;
        public String off;

        public boolean isScalar;
        public float value1;
        public float value2;
        public float value3;

        boolean isNothing;

        public Control(String _on, String _off, float v1, float v2, float v3) {
            this.on = _on;
            this.off = _off;

            this.value1 = v1;
            this.value2 = v2;
            this.value3 = v3;
            this.isScalar = false;
        }
        public Control(String _on, float v1, float v2) {
            this.on = _on;
            this.off = "";

            this.value1 = v1;
            this.value2 = v2;
            this.isScalar = false;
        }

        public Control(String _on) {
            this.on = _on;
            this.off = "";
            this.isScalar = true;
        }

        public Control(float val) {
            this.on = "";
            this.off = "";

            this.isNothing = true;
            this.value1 = val;
        }





    }
