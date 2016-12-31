package control;

import java.util.HashMap;
import java.util.Map;

import control.Controller.Direction;
import control.Controller.Action;
import control.Controller.KeyState;

public class Input {
    private Map<Direction, KeyState> keyStates;

    public Input() {
        keyStates = new HashMap<>();
        for (Direction ctrl : Direction.values())
            keyStates.put(ctrl, KeyState.RELEASED);
    }

    Map<Direction, KeyState> getKeyStates() {
        return keyStates;
    }

    public boolean isPressed(Direction ctrl) {
        return keyStates.get(ctrl) == KeyState.PRESSED;
    }

    public boolean isHeld(Direction ctrl) {
        return keyStates.get(ctrl) == KeyState.HELD;
    }

    public boolean isReleased(Direction ctrl) {
        return keyStates.get(ctrl) == KeyState.RELEASED;
    }

    public boolean isOn(Direction ctrl) {
        return !isReleased(ctrl);
    }

    public boolean isOn(Action action) { return false; }    // TODO

    public Direction getMouseDirection() {
        return null;
    }   // TODO
}
