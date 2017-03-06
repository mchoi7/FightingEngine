package service;

public abstract class Task {
    private int start, duration;

    public Task(int start) {
        this(start, 1);
    }

    public Task(int start, int duration) {
        this.start = start;
        this.duration = duration;
    }

    protected void init() {}

    protected abstract void run();

    public boolean isReady(int time) {
        return time >= start && time < start + duration;
    }
}