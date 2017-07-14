package br.uece.beethoven.dsl;

import java.util.HashSet;

import static java.util.Objects.requireNonNull;

public class WorkflowBuilder {

    private Workflow workflow;

    public WorkflowBuilder() {
        this.workflow = new Workflow();
    }

    public static WorkflowBuilder workflow() {
        return new WorkflowBuilder();
    }

    public WorkflowBuilder name(String name) {
        requireNonNull(name);
        this.workflow.setName(name);
        return this;
    }

    public WorkflowBuilder tasks(Task... tasks) {
        this.workflow.setTasks(new HashSet<>());
        for (Task task : tasks) {
            workflow.getTasks().add(task);
        }

        return this;
    }

    public Workflow eventHandlers(EventHandler... eventHandlers) {
        this.workflow.setEventHandlers(new HashSet<>());
        for (EventHandler eventHandler : eventHandlers) {
            workflow.getEventHandlers().add(eventHandler);
        }

        return this.workflow;
    }

}
