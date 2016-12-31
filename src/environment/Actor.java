package environment;

import assist.RenderAssistant;
import geometry.Box;
import tag.*;

import java.awt.*;

import static java.lang.Math.abs;

public abstract class Actor extends Box implements Updatable, Renderable, Respondable, Collidable, Removable {
    public Actor(double x, double y, double w, double h) {
        super(x, y, w, h);
    }

    public boolean isColliding(Collidable collidable) {
        return intersects(collidable.getBound());
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
                double lx = getLx(), ly = getLy();
                Box bound = collidable.getBound();
                if (getW() + bound.getW() - 2 * abs(getX() - bound.getX()) > getH() + bound.getH() - 2 * abs(getY() - bound.getY())) {
                    //y collision
                } else {
                    //x collision
                }
                break;
            case FLUID:
                break;
        }
    }
}
