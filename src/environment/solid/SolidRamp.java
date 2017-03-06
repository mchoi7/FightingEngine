package environment.solid;

import geometry.Box;
import tag.Collidable;
import tag.Renderable;
import tag.Respondable;

import java.awt.*;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

public class SolidRamp extends Box implements Renderable, Collidable {
    private double w, h;
    public SolidRamp(double x, double y, double w, double h) {
        super(x, y, abs(w), abs(h));
        this.w = w;
        this.h = h;
    }

    public void render(Graphics2D g) {
        g.setColor(Color.white);
        g.drawLine((int) (getX() - w / 2), (int) (getY() - h / 2), (int) (getX() + w / 2), (int) (getY() + h / 2));
        g.drawLine((int) (getX() - w / 2), (int) (getY() + h / 2), (int) (getX() + w / 2), (int) (getY() + h / 2));
        g.drawLine((int) (getX() - w / 2), (int) (getY() + h / 2), (int) (getX() - w / 2), (int) (getY() - h / 2));
    }

    public int getDepth() {
        return 999;
    }

    /* TODO */
    public boolean isColliding(Respondable respondable) {
        Box bound = respondable.getBound();

        boolean xoverlapping = 2 * abs(getX() - bound.getX()) < getW() + bound.getW();

        double deltaX = getX() - bound.getX();
        return 2 * abs(deltaX) < getW() + bound.getW() && 2 * abs(getY() - bound.getY()) < deltaX / getW() * (getH() + bound.getH());
    }

    public void collide(Respondable respondable) {
        System.out.println("sout");
    }
}