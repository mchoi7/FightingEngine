package environment.solid;

import assist.RenderAssistant;
import geometry.Box;
import tag.Collidable;
import tag.Renderable;
import tag.Respondable;

import java.awt.*;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

public class SolidBox extends Box implements Renderable, Collidable {
    public SolidBox(double x, double y, double w, double h) {
        super(x, y, w, h);
    }

    public void render(Graphics2D g) {
        g.setColor(Color.black);
        RenderAssistant.drawRect(this, g);
    }

    public int getDepth() {
        return 999;
    }

    public boolean isColliding(Respondable respondable) {
        return intersects(respondable.getBound());
    }

    public void collide(Respondable respondable) {
        Box bound = respondable.getBound();
        double xOverlap = (getW() + bound.getW()) / 2 - abs(getX() - bound.getLx());
        double yOverlap = (getH() + bound.getH()) / 2 - abs(getY() - bound.getLy());
        if (xOverlap < yOverlap) {
            bound.adjust(getX() + signum(bound.getX() - getX()) * (getW() + bound.getW()) / 2, bound.getY());
        } else {
            bound.adjust(bound.getX(), getY() + signum(bound.getY() - getY()) * (getH() + bound.getH()) / 2);
        }
    }
}
