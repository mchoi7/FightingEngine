package environment.character;

import environment.Player;
import geometry.Point;

public class Luna extends Player {
    private static final double WIDTH = 40, HEIGHT = 70;

    public Luna(double x, double y) {
        super(x, y, WIDTH, HEIGHT);
    }

    protected void initialize() {
        setAnimation("LunaIdle");
        setImageSpeed(0.04);
        vel = new Point(0, 0);
        max = new Point(5, 5);
        acc = new Point(3, 3);
        fric = new Point(1, 1);
        grav = new Point(0, 0);
    }

    protected void gndUp() {

    }

    protected void gndDown() {

    }

    protected void gndLeft() {

    }

    protected void gndRight() {

    }

    protected void runUp() {

    }

    protected void runDown() {

    }

    protected void runLeft() {

    }

    protected void runRight() {

    }

    protected void airUp() {

    }

    protected void airDown() {

    }

    protected void airLeft() {

    }

    protected void airRight() {

    }
}