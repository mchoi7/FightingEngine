package service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Action {
    public final static Action empty = new Action(0, -1);
    private List<Task> tasks;
    private Callable<Integer> end;
    private int currentEnd;
    private final int priority;
    private int time;

    public Action(Callable<Integer> end, int priority) {
        this.end = end;
        this.priority = priority;
        tasks = new ArrayList<>();
        init();
    }

    public Action(int end, int priority) {
        this.end = () -> end;
        this.priority = priority;
        tasks = new ArrayList<>();
        init();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void init() {
        time = 0;
        try {
            currentEnd = end.call();
        } catch (Exception e) {
            currentEnd = 0;
            e.printStackTrace();
        }
        tasks.forEach(Task::init);
    }

    public final void tick() {
        tasks.stream().filter(task -> task.isReady(time)).forEach(Task::run);
        time++;
    }

    public final boolean isDone() {
        return time >= currentEnd;
    }

    public int getPriority() {
        return priority;
    }
}