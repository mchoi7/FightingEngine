package environment.structure;

import assist.Constants;
import assist.RenderAssistant;
import control.Controller;
import geometry.ComplexBox;
import service.Action;
import geometry.Point;
import service.Executor;
import service.Task;
import tag.*;
import visual.Animation;
import visual.VisualManager;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.lang.Math.*;

public abstract class Player extends Actor implements Hurtable, Requestable, Teamable {
    private static final double EPSILON = 0.0001;

    protected enum State {
        GND, RUN, AIR
    }

    protected List<Object> requestables;
    protected Point vel, grav, rest, comm, force;
    protected Map<State, Point> max, acc, fric, pow;
    protected Map<State, Map<Controller.Action, Action>> attacks;
    protected Executor executor = new Executor();

    private Team team;
    protected State state = State.AIR;
    private Map<Controller.Action, Boolean> tangents;
    private ComplexBox hurtBox;
    private double health, weight = 10;

    protected Animation animation;
    protected double imageIndex, imageSpeed;
    protected Point scale;
    private List<String> message = new ArrayList<>();

    protected Action land, jump, rebound;

    public Player(double x, double y, double w, double h, Team team) {
        super(x, y, w, h);
        this.team = team;
        requestables = new ArrayList<>();
        tangents = new EnumMap<>(Controller.Action.class);
        tangents.put(Controller.Action.UP, false);
        tangents.put(Controller.Action.DOWN, false);
        tangents.put(Controller.Action.LEFT, false);
        tangents.put(Controller.Action.RIGHT, false);
        vel = new Point(0, 0);
        grav = new Point(0, 0);
        rest = new Point(0, 0);
        comm = new Point(0, 0);
        scale = new Point(1, 1);
        force = new Point(0, 0);
        max = new HashMap<>();
        acc = new HashMap<>();
        fric = new HashMap<>();
        pow = new HashMap<>();
        attacks = new HashMap<>();
        hurtBox = new ComplexBox(this, scale);
        for (State state : State.values()) {
            max.put(state, new Point(0, 0));
            acc.put(state, new Point(0, 0));
            fric.put(state, new Point(0, 0));
            pow.put(state, new Point(0, 0));
            attacks.put(state, new HashMap<>());
            attacks.get(state).put(Controller.Action.UP, Action.empty);
            attacks.get(state).put(Controller.Action.DOWN, Action.empty);
            attacks.get(state).put(Controller.Action.LEFT, Action.empty);
            attacks.get(state).put(Controller.Action.RIGHT, Action.empty);
        }
        rebound = new Action(4, 0);
        rebound.addTask(new Task(4) {
            private double vx, vy;

            protected void init() {
                vx = vel.getX();
                vy = vel.getY();
            }

            protected void run() {
                force.add(vx, -vy);
            }
        });
        initialize();
    }

    public void command(Controller controller) {
        message.add(controller.getComboList().toString());
        if (executor.getAction().isDone()) {
            if (tangents.get(Controller.Action.DOWN) && controller.isPressed(Controller.Action.SPACE))
                executor.setAction(jump);
            double dx = acc.get(state).getX() * ((controller.isOn(Controller.Action.RIGHT) ? 1 : 0) -
                    (controller.isOn(Controller.Action.LEFT) ? 1 : 0));
            double dy = acc.get(state).getY() * ((controller.isOn(Controller.Action.DOWN) ? 1 : 0) -
                    (controller.isOn(Controller.Action.UP) ? 1 : 0));
            comm.add(dx, dy);
            if ((controller.isPressed(Controller.Action.LEFT) && controller.isPerformed(Constants.ll)) ||
                    (controller.isPressed(Controller.Action.RIGHT) && controller.isPerformed(Constants.rr))) {
                comm.add((controller.isOn(Controller.Action.RIGHT) ? pow.get(state).getX() : 0) -
                        (controller.isOn(Controller.Action.LEFT) ? pow.get(state).getX() : 0), 0);
            }
        }
        if (controller.isPressed(Controller.Action.LMB)) {
            Controller.Action direction = controller.getMouseDirection();
            if (scale.getX() < 0) {
                if (direction == Controller.Action.LEFT)
                    direction = Controller.Action.RIGHT;
                else if (direction == Controller.Action.RIGHT)
                    direction = Controller.Action.LEFT;
            }
            executor.setAction(attacks.get(state).get(direction));
        }
    }

    public void update() {
        double px = vel.getX() + getDx(), py = vel.getY() + getDy();
        tangents.put(Controller.Action.UP, false);
        tangents.put(Controller.Action.DOWN, false);
        tangents.put(Controller.Action.LEFT, false);
        tangents.put(Controller.Action.RIGHT, false);
        tangents.put(px > 0 ? Controller.Action.RIGHT : Controller.Action.LEFT, abs(px) > EPSILON);
        tangents.put(py > 0 ? Controller.Action.DOWN : Controller.Action.UP, abs(py) > EPSILON);

        State lastState = state;
        if (tangents.get(Controller.Action.DOWN)) {
            if (abs(comm.getX()) > acc.get(state).getX())
                state = State.RUN;
            else if (state == State.AIR) {
                state = State.GND;
            } else if (abs(vel.getX()) > max.get(State.GND).getX())
                state = State.RUN;
            else state = State.GND;
        } else state = State.AIR;

        vel.add(force.takeX(), force.takeY());
        if (abs(vel.getX()) < fric.get(state).getX()) vel.mux(0, 1);
        else vel.add(-signum(vel.getX()) * fric.get(state).getX(), 0);
        if (abs(vel.getY()) < fric.get(state).getY()) vel.mux(1, 0);
        else vel.add(0, -signum(vel.getY()) * fric.get(state).getY());
        vel.add(comm.takeX(), comm.takeY(), max.get(state).getX(), max.get(state).getY());
        vel.add(grav.getX(), grav.getY());
        vel.mux(tangents.get(Controller.Action.LEFT) || tangents.get(Controller.Action.RIGHT) ? rest.getX() : 1,
                tangents.get(Controller.Action.UP) || tangents.get(Controller.Action.DOWN) ? rest.getY() : 1);

        /* Set Tasks */
        if (lastState == State.AIR && state != State.AIR)
            executor.setAction(land);
        else if (abs(px) > weight || abs(py) > weight)
            executor.setAction(rebound);
        executor.tick();

        scale.set(vel.getX() == 0 ? scale.getX() : signum(vel.getX()), 1);
        add(vel.getX(), vel.getY());

        imageIndex += imageSpeed;
        hurtBox.setMultiBox(animation.getSprite(imageIndex).getMultiBox());
        setVisuals();
    }

    public void collide(Respondable respondable) {
        double vdx = 0, vdy = 0;
        vdx += 10 * signum(getX() - respondable.getBound().getX()) / weight;
        double dy = getY() - respondable.getBound().getY();
        if (abs(dy) > EPSILON)
            vdy += 10 * signum(dy) / weight;
        force.add(vdx, vdy);
    }

    public void render(Graphics2D g) {
        super.render(g);
        animation.getSprite(imageIndex).render(this, scale, g);
        if (getTeam() == Team.BLUE) {
            g.setColor(Color.green);
            g.drawString(getTeam().toString(), (int) getX(), (int) getY() - 40);
            message.add("");
            message.add("State " + state);
            message.add("     X    Y");
            message.add(String.format("Pos %-4.0f %-4.0f", getX(), getY()));
            message.add(String.format("Vel %+-4.0f %+-4.0f", vel.getX(), vel.getY()));
            message.add("Tan   " + (tangents.get(Controller.Action.UP) ? '↑' : ' '));
            message.add("    " + (tangents.get(Controller.Action.LEFT) ? '←' : ' ') + "   " + (tangents.get(Controller.Action.RIGHT) ? '→' : ' '));
            message.add("      " + (tangents.get(Controller.Action.DOWN) ? '↓' : ' '));
            RenderAssistant.drawMessage(message, g);
            message.clear();
        }
        g.setColor(Constants.tgreen);
        RenderAssistant.drawRect(hurtBox, g);
    }

    public void hurt(Hittable hittable) {
        health -= hittable.getDamage();
        force.add(hittable.getKnockback().getX(), hittable.getKnockback().getY());
    }

    public boolean isInteracting(Hittable hittable) {
        if (hittable.getTeam() != getTeam())
            if (hittable.getComplexBox().intersects(getComplexBox()))
                return true;
        return false;
    }

    public ComplexBox getComplexBox() {
        return hurtBox;
    }

    public List getRequests() {
        return requestables;
    }

    protected abstract void setVisuals();

    public Team getTeam() {
        return team;
    }

    protected abstract void initialize();

    public int getDepth() {
        return -1;
    }

    protected void setAnimation(String name) {
        Animation newAnimation = VisualManager.getAnimation(name);
        imageSpeed = 0;
        if (animation != newAnimation) {
            animation = newAnimation;
            imageIndex = 0;
        }
    }
}