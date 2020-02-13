package org.firstinspires.ftc.teamcode.managers;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.*;
import org.firstinspires.ftc.teamcode.auxilary.*;
import org.firstinspires.ftc.teamcode.managers.*;
import org.firstinspires.ftc.teamcode.teleop.*;

public class MovementManager extends FeatureManager {

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    /**
     * Create a MovementManager with four motors.
     * @param fl Front Left motor
     * @param fr Front Right motor
     * @param br Back Right motor
     * @param bl Back Left motor
     */

    private static float speed = 1.0f;
    private int avg;


    private LinearOpMode opMode;

    public MovementManager(DcMotor fl, DcMotor fr, DcMotor br, DcMotor bl) {
        this.frontLeft = fl;
        this.frontRight = fr;
        this.backRight = br;
        this.backLeft = bl;
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void driveRaw(float fl, float fr, float br, float bl) {
        frontLeft.setPower(fl);
        frontRight.setPower(fr);
        backRight.setPower(br);
        backLeft.setPower(bl);
    }

    public void stopDrive() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
    }

    public void driveOmni(float[] powers) {
        float[] sum = PaulMath.omniCalc(powers[0], powers[1], powers[2]);
        driveRaw(sum[0], sum[1], sum[2], sum[3]);
    }

    public void driveOmniExponential(float verticalPower, float horizontalPower, float rotationalPower) {
        float[] sum = PaulMath.omniCalc(
                (float)Math.pow(verticalPower, EXPONENTIAL_SCALAR),
                (float)Math.pow(horizontalPower, EXPONENTIAL_SCALAR),
                (float)Math.pow(rotationalPower, EXPONENTIAL_SCALAR));
        driveRaw(sum[0], sum[1], sum[2], sum[3]);
    }

    public void resetEncoders() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void runToPosition() {
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void runUsingEncoders() {
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void runWithOutEncoders() {
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setTargetPositions(int fl, int fr, int br, int bl) {
        frontLeft.setTargetPosition(fl);
        frontRight.setTargetPosition(fr);
        backRight.setTargetPosition(br);
        backLeft.setTargetPosition(bl);
    }

    public void driveVertical(float power, float distance) {
        int ticks = PaulMath.encoderDistance(distance);
        setTargetPositions(ticks, ticks, ticks, ticks);
        runToPosition();
        while(
                Math.abs(frontLeft.getCurrentPosition()) < Math.abs(frontLeft.getTargetPosition()) &&
                        Math.abs(frontRight.getCurrentPosition()) < Math.abs(frontRight.getTargetPosition()) &&
                        Math.abs(backRight.getCurrentPosition()) < Math.abs(backRight.getTargetPosition()) &&
                        Math.abs(backLeft.getCurrentPosition()) < Math.abs(backLeft.getTargetPosition())
        ) {
            driveRaw(power, power, power, power);
            //Waiting for motor to finish
        }
        stopDrive();
    }

    public void driveWithVertical(float power, float distance) {
        int ticks = PaulMath.encoderDistance(distance);
        setTargetPositions(ticks, ticks, ticks, ticks);
        runUsingEncoders();
        if(Math.abs(frontLeft.getCurrentPosition()) < Math.abs(frontLeft.getTargetPosition()) &&
                        Math.abs(frontRight.getCurrentPosition()) < Math.abs(frontRight.getTargetPosition()) &&
                        Math.abs(backRight.getCurrentPosition()) < Math.abs(backRight.getTargetPosition()) &&
                        Math.abs(backLeft.getCurrentPosition()) < Math.abs(backLeft.getTargetPosition())
        ) {
            driveRaw(power, power, power, power);
            //Waiting for motor to finish
        } else {
            stopDrive();
        }

    }

}
