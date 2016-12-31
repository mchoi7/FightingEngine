package environment;

import assist.RenderAssistant;
import geometry.Box;
import tag.Collidable;
import tag.Renderable;

import java.awt.*;

public class Solid extends Box implements Renderable, Collidable {
    public Solid(double x, double y, double w, double h) {
        super(x, y, w, h);
    }

    public void render(Graphics2D g) {
        g.setColor(Color.white);
        RenderAssistant.drawRect(this, g);
    }

    public Box getBound() {
        return this;
    }

    public Attribute getAttribute() {
        return Attribute.FIXED;
    }
}
