package org.firstinspires.ftc.teamcode.auxilary;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.managers.FeatureManager;

public class PaulMath extends FeatureManager {

    public static float highestValue(float[] array) {
        int arrayLength = array.length;
        float highest = -1000000;
        for (int i = 0; i < arrayLength; i++) {
            if(array[i] > highest) {
                highest = array[i];
            }
        }
        return highest;
    }

    public static float[] normalizeArray(float[] array) {
        int arrayLength = array.length;
        float highest = highestValue(array);
        for (int i = 0; i < arrayLength; i++) {
            array[i] = array[i] / highest;
        }
        return array;
    }

    public static float[] omniCalc(float verticalPower, float horizontalPower, float rotationalPower) {
        float lx = Range.clip(horizontalPower, -1, 1);
        float lY = Range.clip(verticalPower, -1, 1);
        float rx = Range.clip(rotationalPower, -1, 1);

        // Motor powers of fl, fr, br, bl
        // Motor powers used to be 0.4f for all motors other than fl
        float[] vertical = {lY, -lY, lY, -lY};
        float[] horizontal = {-lx, -lx, lx, lx};
        float[] rotational = {-rx, -rx, -rx, -rx};
//
//        float[] vertical = {-lY, lY, -lY, lY};
//        float[] horizontal = {lx, 0.7f*lx, -0.7f*lx, -0.7f*lx};
//        float[] rotational = {rx, rx, rx, rx};

        float[] sum = new float[4];
        for (int i = 0; i < 4; i++) {
            sum[i] = vertical[i] + horizontal[i] + rotational[i];
        }
        //This makes sure that no value is greater than 1 by dividing all of them by the maximum
        float highest = highestValue(sum);
        if(highest > 1) {
            normalizeArray(sum);
        }
        return sum;
    }

    public static float roundToPoint(float input, float place) {
        return Math.round(input / place) * place;
    }

    public static int encoderDistance(double distance) {
        double ROTATIONS = distance / CIRCUMFERENCE;
        int counts =  (int)((ENCODER_CPR * ROTATIONS * GEAR_RATIO)/SLIP);
        return counts;
    }


}
