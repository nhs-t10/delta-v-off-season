package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.teleop.*;
import org.firstinspires.ftc.teamcode.managers.*;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.auxilary.*;


@TeleOp
public class Teleop extends OpMode {

    InputManager input;
    MovementManager driver;
    ColorSensor sensor;
    Servo grab;
    boolean grabBool = false;


    private static boolean toggleSpeed = false;

    public void init() {
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));
//        sensor = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensor"));
        grab = hardwareMap.get(Servo.class, "grab");
        input = new InputManager(gamepad1);
        driver.resetEncoders();
        driver.runUsingEncoders();
    }

    public void loop() {
        if(!input.getGamepad().left_bumper) {
            driver.driveOmni(input.getMovementControls());
        } else {
            driver.driveOmniExponential(input.getMovementControls());
        }

        if(input.getGamepad().a){
            if (!grabBool) {
                grab.setPosition(1);
                grabBool = true;
            } else if (grabBool) {
                grab.setPosition(0);
                grabBool = false;
            }
        }

        telemetry.addData("FL Ticks:", driver.frontLeft.getCurrentPosition());
        telemetry.addData("FR Ticks:", driver.frontRight.getCurrentPosition());
        telemetry.addData("BL Ticks:", driver.backRight.getCurrentPosition());
        telemetry.addData("BR Ticks:", driver.backLeft.getCurrentPosition());
        telemetry.addData("Average Ticks:", (driver.frontLeft.getCurrentPosition()+
                driver.frontRight.getCurrentPosition()+
                driver.backLeft.getCurrentPosition()+
                driver.backRight.getCurrentPosition())/4);

        telemetry.addData("Servo Position", grab.getPosition());

        telemetry.addData("FL Power: ", driver.frontLeft.getPower());
        telemetry.addData("FL Port: ", driver.frontLeft.getPortNumber());

        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("FR Port: ", driver.frontRight.getPortNumber());

        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BL Port: ", driver.backLeft.getPortNumber());

        telemetry.addData("BR Power: ", driver.backRight.getPower());
        telemetry.addData("BR Port: ", driver.backRight.getPortNumber());
    }
}