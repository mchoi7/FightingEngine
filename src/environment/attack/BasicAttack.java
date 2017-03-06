package environment.attack;

import assist.RenderAssistant;
import geometry.ComplexBox;
import geometry.MultiBox;
import geometry.Point;
import tag.*;

import java.awt.*;
import java.util.List;

public class BasicAttack extends ComplexBox implements Hittable, Updatable, Renderable, Removable {
    private List<MultiBox> multiBoxes;
    private double boundsIndex, boundsSpeed = .25;
    private boolean finished;
    private Team team;
    private Point knockback;

    public BasicAttack(Point link, Point scale, List<MultiBox> multiBoxes, Team team) {
        super(link, scale);
        this.multiBoxes = multiBoxes;
        this.team = team;
        knockback = new Point(0, 0);
    }

    public Point getKnockback() {
        return knockback;
    }

    public Team getTeam() {
        return team;
    }

    public void update() {
        setMultiBox(multiBoxes.get((int) boundsIndex % multiBoxes.size()));
        boundsIndex += boundsSpeed;
        if (boundsIndex >= multiBoxes.size())
            finished = true;
    }

    public double getDamage() {
        finished = true;
        return 0;
    }

    public ComplexBox getComplexBox() {
        return this;
    }

    public void render(Graphics2D g) {
        g.setColor(Color.RED);
        RenderAssistant.drawRect(this, g);
    }

    public int getDepth() {
        return -2;
    }

    public boolean isFinished() {
        return finished;
    }
}
