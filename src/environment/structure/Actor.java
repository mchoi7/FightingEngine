package environment.structure;

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

    public boolean isColliding(Respondable respondable) {
        return this != respondable && intersects(respondable.getBound());
    }

    public void render(Graphics2D g) {
        g.setColor(Color.blue);
        RenderAssistant.drawRect(this, g);
    }

    public Box getBound() {
        return this;
    }
}
