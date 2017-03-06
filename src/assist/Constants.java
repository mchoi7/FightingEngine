package assist;

import control.Controller.Action;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final List<Action> ll = new ArrayList<>(Arrays.asList(Action.LEFT, Action.LEFT));
    public static final List<Action> rr = new ArrayList<>(Arrays.asList(Action.RIGHT, Action.RIGHT));
    public static final Color tgreen = new Color(0, 255, 0, 100);
}
