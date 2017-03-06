package environment.structure;

import assist.RenderAssistant;
import control.GameFrame;
import geometry.Point;
import tag.*;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Scene {
    private double FPS = 60;
    private ListsMap lists = new ListsMap();
    private Point camera = new Point(400, 400), focus = Point.origin, mouse = Point.origin;
    private double zoom;

    public void update() {
        lists.get(Updatable.class).stream().filter(Updatable::isAwake).forEach(Updatable::update);
        for (Requestable requestable : lists.get(Requestable.class)) {
            lists.addAll(requestable.getRequests());
            requestable.getRequests().clear();
        }
        lists.removeAll(lists.get(Removable.class).stream().filter(Removable::isFinished).collect(Collectors.toList()));

        for (Collidable collidable : lists.get(Collidable.class))
            lists.get(Respondable.class).stream().filter(collidable::isColliding).forEach(collidable::collide);
        for (Hurtable hurtable : lists.get(Hurtable.class))
            lists.get(Hittable.class).stream().filter(hurtable::isInteracting).forEach(hurtable::hurt);

        double focusWeight = .15, mouseWeight = 0.2, mouseOffset = 40;
        camera.set(focusWeight * focus.getX() + mouseWeight * (mouseOffset * mouse.getX() + camera.getX()) + (1 - focusWeight - mouseWeight) * camera.getX(),
                focusWeight * focus.getY() + mouseWeight * (mouseOffset * mouse.getY() + camera.getY()) + (1 - focusWeight - mouseWeight) * camera.getY());
    }

    public void render(Graphics2D g) {
        double scale = Math.pow(1.1, zoom);
        g.translate(-camera.getX() + GameFrame.WIDTH / 2, -camera.getY() + GameFrame.HEIGHT / 2);
        g.translate((1 - scale) * camera.getX(), (1 - scale) * camera.getY());
        g.scale(scale, scale);
        Collections.sort(lists.get(Renderable.class), (r1, r2) -> r2.getDepth() - r1.getDepth());
        lists.get(Renderable.class).stream().filter(Renderable::isVisible).forEach(renderable -> renderable.render(g));
    }

    public void add(Object obj) {
        lists.add(obj);
    }

    public void setFocus(Point focus) {
        this.focus = focus;
        camera.set(focus.getX(), focus.getY());
    }

    public void setMouse(Point mouse) {
        this.mouse = mouse;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    private class ListsMap {
        private Map<Class, List> maps = new HashMap<>();

        public ListsMap() {
            maps.put(Updatable.class, new ArrayList());
            maps.put(Renderable.class, new ArrayList());
            maps.put(Requestable.class, new ArrayList());
            maps.put(Removable.class, new ArrayList());
            maps.put(Respondable.class, new ArrayList());
            maps.put(Collidable.class, new ArrayList());
            maps.put(Hurtable.class, new ArrayList());
            maps.put(Hittable.class, new ArrayList());
            maps.put(Teamable.class, new ArrayList());
        }

        private <T> List<T> get(Class<T> clazz) {
            return maps.get(clazz);
        }

        private void add(Object obj) {
            Class clazz = obj.getClass();
            while (clazz != null) {
                for (Type type : clazz.getGenericInterfaces())
                    maps.get(type).add(obj);
                clazz = clazz.getSuperclass();
            }
        }

        private void addAll(List objects) {
            objects.forEach(this::add);
        }

        private void remove(Object garbage) {
            maps.values().forEach(list -> list.remove(garbage));
        }

        private void removeAll(List garbage) {
            garbage.forEach(this::remove);
        }
    }
}
