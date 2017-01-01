package control;

import environment.Player;

import java.util.HashMap;
import java.util.Map;

public class Controller {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    public enum Action {
        SPACE, LMB, RMB
    }

    public enum KeyState {
        PRESSED, HELD, RELEASED
    }

    private static final int RESETDELAY = 24;
    private Map<Direction, KeyState> keyStates;
    private Map<Direction, Character> keyMapping;
    private Player player;

    Controller() {
        keyMapping = new HashMap<>();
        keyStates = new HashMap<>();
        mapInput(Direction.UP, 'W');
        mapInput(Direction.DOWN, 'S');
        mapInput(Direction.LEFT, 'D');
        mapInput(Direction.RIGHT, 'A');
    }

    void update(GameFrame gameFrame) {
        for(Direction dir : Direction.values()) {
            if (gameFrame.isKeyOn(keyMapping.get(dir))) {
                if(keyMapping.get(dir) == 'W')
                    System.out.println(dir);
                if (keyStates.get(dir) == KeyState.RELEASED) {
                    keyStates.put(dir, KeyState.PRESSED);
                    //combo record here
                } else
                    keyStates.put(dir, KeyState.HELD);
            } else
                keyStates.put(dir, KeyState.RELEASED);
        }
        if (player != null)
            player.command(this);
    }

    void mapInput(Direction dir, char ch) {
        keyMapping.put(dir, ch);
    }

    void setPlayer(Player player) {
        this.player = player;
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
        return keyStates.get(ctrl) != KeyState.RELEASED;
    }

    public boolean isOn(Action action) { return false; }    // TODO

    public Direction getMouseDirection() {
        return null;
    }   // TODO
}
