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
    private Map<Direction, Character> keyMapping = new HashMap<>();
    private Input input;
    private Player player;

    public Controller() {
        keyMapping = new HashMap<>();
        input = new Input();
        mapInput(Direction.UP, 'W');
        mapInput(Direction.DOWN, 'S');
        mapInput(Direction.LEFT, 'D');
        mapInput(Direction.RIGHT, 'A');
    }

    void update(GameFrame gameFrame) {
        for(Direction dir : Direction.values()) {
            if (gameFrame.isKeyOn(keyMapping.get(dir))) {
                if (input.getKeyStates().get(dir) == KeyState.RELEASED) {
                    input.getKeyStates().put(dir, KeyState.PRESSED);
                    //combo record here
                } else
                    input.getKeyStates().put(dir, KeyState.HELD);
            } else
                input.getKeyStates().put(dir, KeyState.RELEASED);
        }
        if (player != null)
            player.command(input);
    }

    public void mapInput(Direction dir, char ch) {
        keyMapping.put(dir, ch);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
