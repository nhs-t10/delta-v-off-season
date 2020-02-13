package org.firstinspires.ftc.teamcode.managers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.teleop.*;
import org.firstinspires.ftc.teamcode.managers.*;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.auxilary.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Handle input (button combos, keybinds, etc.) for gamepads.
 */
public class InputManager extends FeatureManager {
    public Gamepad gamepad;

    public static String lastKey;

    public float currentSpeed = 0.6f;

    public boolean dpad_leftPress = false;
    public boolean dpad_rightPress = false;
    public boolean dpad_leftBumper = false;

    public HashMap<String,Float> lastPresses = new HashMap<>();
    public HashMap<String,Boolean> togglePresses = new HashMap<>();

    public InputManager(Gamepad _gamepad) {
        this.gamepad = _gamepad;
    }

    public Gamepad getGamepad() {
        return this.gamepad;
    }

    public boolean combo(String buttons) {
        char[] buttonLetters = buttons.toCharArray();
        boolean result = true;
        for (int i = 0; i < buttonLetters.length; i++) {
            char letter = buttonLetters[i];
            if (letter == 'A' && !gamepad.a) result = false;
            if (letter == 'B' && !gamepad.b) result = false;
            if (letter == 'X' && !gamepad.x) result = false;
            if (letter == 'Y' && !gamepad.y) result = false;
        }
        if (gamepad.timestamp > INPUT_DOUBLECLICK_TIME) result = false;

        return result;
    }

    public float[] getMovementControls() {
        return new float[] {resolveControl(ControlMap.MOVE_VERTICAL),
                resolveControl(ControlMap.MOVE_HORIZONTAL),
                resolveControl(ControlMap.MOVE_ROTATIONAL)};
    }

    /**
     * Get the current state of a button on the gamepad
     * @param button The name of the button to get the state of
     * @return The current state of the button
     */
    public float getButtonState(String button) {
        switch(button) {
            case "left_stick_x":
                return gamepad.left_stick_x;
            case "left_stick_y":
                return gamepad.left_stick_y;

            case "right_stick_x":
                return gamepad.right_stick_x;
            case "right_stick_y":
                return gamepad.right_stick_y;

            case "left_trigger":
                return gamepad.left_trigger;
            case "right_trigger":
                return gamepad.right_trigger;

            case "left_bumper":
                return gamepad.left_bumper?1f:0f;
            case "right_bumper":
                return gamepad.right_bumper?1f:0f;

            case "a":
                return gamepad.a?1f:0f;
            case "b":
                return gamepad.b?1f:0f;
            case "x":
                return gamepad.x?1f:0f;
            case "y":
                return gamepad.y?1f:0f;

            case "dpad_left":
                return gamepad.dpad_left?1f:0f;
            case "dpad_right":
                return gamepad.dpad_right?1f:0f;
            case "dpad_up":
                return gamepad.dpad_up?1f:0f;
            case "dpad_down":
                return gamepad.dpad_down?1f:0f;

            default:
                return 0f;
        }
    }

    public float resolveControlString(String control) {
        if(control.toLowerCase().equals(control)) return getButtonState(control);
        else return combo(control)?1f:0f;
    }

    public float resolveControl(Control control) {
        //if it's a scalar
        if (control.isScalar) {
            return resolveControlString(control.on);
        }
        //if is 1-button toggle
        else if (control.off.isEmpty()) {
            return resolveControlString(control.on) > 0.1?control.value1:control.value2;
        }
        //if it's a scalar

        //if it's a 2-button toggle
        else {
            if(resolveControlString(control.on) > 0f) return control.value1;
            else if(resolveControlString(control.off) > 0f) return control.value2;
            else return control.value3;
        }
    }
}

