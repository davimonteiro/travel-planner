package br.uece.beethoven.dsl;


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

    public Task doIt(HttpRequest httpRequest) {
        requireNonNull(httpRequest);
        task.setHttpRequest(httpRequest);
        return this.task;
    }

}
