package environment;

import assist.RenderAssistant;
import control.Input;
import control.Controller.Direction;
import control.Controller.Action;
import geometry.Box;
import geometry.Point;
import tag.Hittable;
import tag.Hurtable;
import visual.Animation;
import visual.VisualManager;

import java.awt.*;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

public abstract class Player extends Actor implements Hurtable {
    private enum State {
        GND, RUN, AIR
    }

    protected Point vel, max, acc, fric, grav;
    private List<Box> bounds;
    private State state;
    private double health;

    private Animation animation;
    private double imageIndex, imageSpeed;

    public Player(double x, double y, double w, double h) {
        super(x, y, w, h);
        initialize();
    }

    protected abstract void initialize();

    public void update() {
        if (abs(vel.getX()) < fric.getX()) vel.setX(0);
        else vel.addX(-signum(vel.getX()) * fric.getX());
        if (abs(vel.getY()) < fric.getY()) vel.setY(0);
        else vel.addY(-signum(vel.getY()) * fric.getY());
        vel.addX(grav.getX());
        vel.addY(grav.getY());
        add(vel.getX(), vel.getY());
        imageIndex += imageSpeed;
    }

    public void render(Graphics2D g) {
        super.render(g);
        animation.getSprite(imageIndex).render(this, g);
        g.setColor(Color.green);
        for (Box box : animation.getSprite(imageIndex).getBounds())
            RenderAssistant.drawRect(box, this, g);
    }

    public void command(Input input) {
        vel.setX(sumTo(vel.getX(), (input.isOn(Direction.LEFT) ? acc.getX() : 0) - (input.isOn(Direction.RIGHT) ? acc.getX() : 0), max.getX()));
        vel.setY(sumTo(vel.getY(), (input.isOn(Direction.DOWN) ? acc.getY() : 0) - (input.isOn(Direction.UP) ? acc.getY() : 0), max.getY()));

        if (input.isOn(Action.LMB)) {
            switch (state) {
                case GND:
                    switch (input.getMouseDirection()) {
                        case UP:
                            gndUp();
                            break;
                        case DOWN:
                            gndDown();
                            break;
                        case LEFT:
                            gndLeft();
                            break;
                        case RIGHT:
                            gndRight();
                            break;
                    }
                    break;
                case RUN:
                    switch (input.getMouseDirection()) {
                        case UP:
                            runUp();
                            break;
                        case DOWN:
                            runDown();
                            break;
                        case LEFT:
                            runLeft();
                            break;
                        case RIGHT:
                            runRight();
                            break;
                    }
                    break;
                case AIR:
                    switch (input.getMouseDirection()) {
                        case UP:
                            airUp();
                            break;
                        case DOWN:
                            airDown();
                            break;
                        case LEFT:
                            airLeft();
                            break;
                        case RIGHT:
                            airRight();
                            break;
                    }
                    break;
            }
        }
    }

    protected abstract void gndUp();

    protected abstract void gndDown();

    protected abstract void gndLeft();

    protected abstract void gndRight();

    protected abstract void runUp();

    protected abstract void runDown();

    protected abstract void runLeft();

    protected abstract void runRight();

    protected abstract void airUp();

    protected abstract void airDown();

    protected abstract void airLeft();

    protected abstract void airRight();

    public void hurt(Hittable hittable) {
        health -= hittable.getDamage();
    }

    public boolean isInteracting(Hittable hittable) {
        for (Box box : hittable.getBounds())
            for (Box bound : bounds)
                if (box.intersects(bound))
                    return true;
        return false;
    }

    private double sumTo(double x, double y, double lim) {
        if (x < 0 == y < 0) {
            if (abs(x + y) <= lim) return x + y;
            else return x;
        } else {
            if (abs(x + y) >= -lim) return x + y;
            else return signum(y) * lim;
        }
    }

    protected void setAnimation(String name) {
        Animation newAnimation = VisualManager.getAnimation(name);
        if (animation != newAnimation) {
            animation = newAnimation;
            imageIndex = 0;
        }
    }

    protected void setImageSpeed(double imageSpeed) {
        this.imageSpeed = imageSpeed;
    }
}
