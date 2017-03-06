package environment.character;

import environment.structure.Player;
import service.Action;
import service.Task;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class Luna extends Player {
    private static final double WIDTH = 30, HEIGHT = 70;

    public Luna(double x, double y, Team team) {
        super(x, y, WIDTH, HEIGHT, team);
    }

    protected void initialize() {
        setAnimation("LunaIdle");
        grav.set(0, 0.75);
        rest.set(.8, .5);
        max.get(State.GND).set(4, 0);
        max.get(State.RUN).set(5.5, 0);
        max.get(State.AIR).set(3, 15);
        acc.get(State.GND).set(.75, 0);
        acc.get(State.RUN).set(1.25, 0);
        acc.get(State.AIR).set(.05, .25);
        fric.get(State.GND).set(.25, 0);
        fric.get(State.RUN).set(.2, 0);
        fric.get(State.AIR).set(0, 0);
        pow.get(State.GND).set(6, 15);
        pow.get(State.RUN).set(0, 14);
        pow.get(State.AIR).set(1, 13);
        land = new Action(() -> 1 + (int) (8 * min(abs(vel.getY()) / max.get(State.AIR).getY(), 1)), -1);
        jump = new Action(4, -1);
        jump.addTask(new Task(4) {
            private double vx;

            protected void init() {
                vx = vel.getX();
                vel.mux(rest.getX(), rest.getY());
            }

            protected void run() {
                vel.set(vx, -pow.get(state).getY());
            }
        });
    }

    protected void setVisuals() {
        if (executor.getAction().isDone()) {
            if (state == State.AIR) {
                setAnimation("LunaJump");
                if (vel.getY() < -4) {
                    imageIndex = 1;
                } else if (vel.getY() > 3) {
                    imageIndex = 3;
                } else if (vel.getY() != 0) {
                    imageIndex = 2;
                }
            } else {
                if (abs(vel.getX()) <= fric.get(state).getX()) {
                    setAnimation("LunaIdle");
                    imageSpeed = 0.1;
                } else {
                    setAnimation("LunaRun");
                    imageSpeed = 0.05 + 0.15 * abs(vel.getX()) / max.get(State.GND).getX();
                }
            }
        } else {
            if (executor.getAction() == jump) {
                setAnimation("LunaJump");
            } else if (executor.getAction() == land) {
                setAnimation("LunaJump");
                imageIndex = 4;
            }
        }
    }
}