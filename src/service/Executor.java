package service;

public class Executor {
    private Action action = Action.empty;

    public void tick() {
        action.tick();
    }

    public Action setAction(Action action) {
        if (this.action.isDone() || action.getPriority() > this.action.getPriority()) {
            action.init();
            this.action = action;
        }
        return this.action;
    }

    public Action getAction() {
        return action;
    }
}
