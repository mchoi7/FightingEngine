package control;

import environment.structure.Player;

import java.util.*;

public class Controller {

    public enum Action {
        RIGHT, UP, LEFT, DOWN, SPACE, LMB, RMB
    }

    private enum KeyState {
        PRESSED, HELD, RELEASED
    }

    private static final int RESETDELAY = 12;
    private Map<Action, Integer> keyMapping;
    private Map<Action, KeyState> keyStates;
    private List<Action> comboList;
    private Player player;
    private int comboTimer;
    private Action mouseDirection;

    Controller() {
        keyMapping = new HashMap<>();
        keyStates = new HashMap<>();
        comboList = new ArrayList<>();
        mapInput(Action.UP, 'W');
        mapInput(Action.DOWN, 'S');
        mapInput(Action.LEFT, 'A');
        mapInput(Action.RIGHT, 'D');
        mapInput(Action.SPACE, ' ');
        mapInput(Action.LMB, -1);
        mapInput(Action.RMB, -2);
    }

    void update(GameFrame gameFrame) {

        mouseDirection = Action.values()[(int) (Math.round(2 * Math.atan2(-gameFrame.getMouse().getY(), gameFrame.getMouse().getX()) / Math.PI) + 4) % 4];
        for (Action dir : Action.values()) {
            if (gameFrame.isKeyOn(keyMapping.get(dir))) {
                if (keyStates.get(dir) == KeyState.RELEASED) {
                    keyStates.put(dir, KeyState.PRESSED);
                    comboList.add(dir);
                    comboTimer = RESETDELAY;
                } else
                    keyStates.put(dir, KeyState.HELD);
            } else
                keyStates.put(dir, KeyState.RELEASED);
        }

        if(comboTimer <= 0)
            comboList.clear();
        comboTimer--;

        if (player != null)
            player.command(this);
    }

    private void mapInput(Action dir, int ch) {
        keyMapping.put(dir, ch);
    }

    void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isPressed(Action dir) {
        return keyStates.get(dir) == KeyState.PRESSED;
    }

    public boolean isHeld(Action dir) {
        return keyStates.get(dir) == KeyState.HELD;
    }

    public boolean isReleased(Action dir) {
        return keyStates.get(dir) == KeyState.RELEASED;
    }

    public boolean isOn(Action dir) {
        return !isReleased(dir);
    }

    public boolean isPerformed(List<Action> combo) {
        boolean isPerformed = false;
        if(comboList.size() >= combo.size()) {
            isPerformed = comboList.subList(0, combo.size()).equals(combo);
            if (isPerformed)
                comboList.clear();
        }
        return isPerformed;
    }

    public List<Action> getComboList() {
        return comboList;
    }

    public Action getMouseDirection() {
        return mouseDirection;
    }
}