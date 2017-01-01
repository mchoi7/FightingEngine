package environment;

import assist.RenderAssistant;
import geometry.Box;
import tag.*;

import java.awt.*;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

public abstract class Actor extends Box implements Updatable, Renderable, Respondable, Collidable, Removable {
    public Actor(double x, double y, double w, double h) {
        super(x, y, w, h);
    }

    public boolean isColliding(Collidable collidable) {
        return this != collidable && intersects(collidable.getBound());
    }

    public void render(Graphics2D g) {
        g.setColor(Color.white);
        RenderAssistant.drawRect(this, g);
    }

    public Box getBound() {
        return this;
    }

    public Attribute getAttribute() {
        return Attribute.FLUID;
    }

    public void respond(Collidable collidable) {
        switch (collidable.getAttribute()) {
            case FIXED:
                Box bound = collidable.getBound();
                if(bound.getW() * abs(getY() - getLy()) < (bound.getH() * abs(getX() - getLx()))) {
                    setX(bound.getX() + signum(getLx() - bound.getX()) * (getW() + bound.getW()) / 2);
                } else {
                    setY(bound.getY() - signum(getLy() - bound.getY()) * (getH() + bound.getH()) / 2);
                }
                break;
            case FLUID:
                break;
        }
    }
}
