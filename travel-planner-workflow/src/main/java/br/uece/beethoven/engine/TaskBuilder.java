package br.uece.beethoven.engine;


import static java.util.Objects.*;

public class TaskBuilder {

    private Task task;

    public TaskBuilder() {
        this.task = new Task();
    }

    public static TaskBuilder task() {
        return new TaskBuilder();
    }

    public TaskBuilder name(String name) {
        requireNonNull(name);
        task.setName(name);
        return this;
    }

    public Task doIt(Action action) {
        requireNonNull(action);
        task.setAction(action);
        return this.task;
    }

}
