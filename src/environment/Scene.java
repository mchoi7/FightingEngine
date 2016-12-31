package environment;

import tag.*;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Scene implements Updatable, Renderable {
    private ListsMap lists = new ListsMap();

    public void update() {
        /* Update all */
        lists.get(Updatable.class).stream().filter(Updatable::isAwake).forEach(Updatable::update);
        /* Add and clear requests */
        for (Requestable requestable : lists.get(Requestable.class)) {
            lists.addAll(requestable.getRequests());
            requestable.getRequests().clear();
        }
        /* Remove finished */
        lists.removeAll(lists.get(Removable.class).stream().filter(Removable::isFinished).collect(Collectors.toList()));
        /* Responders respond to Collidables */
        for (Respondable respondable : lists.get(Respondable.class))
            lists.get(Collidable.class).stream().filter(respondable::isColliding).forEach(respondable::respond);
        /* Hurtables hurt from Hittables */
        for (Hurtable hurtable : lists.get(Hurtable.class))
            lists.get(Hittable.class).stream().filter(hurtable::isInteracting).forEach(hurtable::hurt);
    }

    public void render(Graphics2D g) {
        /* Render all */
        lists.get(Renderable.class).stream().filter(Renderable::isVisible).forEach(renderable -> renderable.render(g));
    }

    public void add(Object obj) {
        lists.add(obj);
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
        }

        private <T> List<T> get(Class<T> clazz) {
            return maps.get(clazz);
        }

        private void add(Object obj) {
            Class clazz = obj.getClass();
            while(clazz != null) {
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
            maps.values().forEach(garbage::remove);
        }
    }
}
