package br.uece.beethoven.engine;

import java.util.HashSet;
import java.util.Set;

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

    public Workflow tasks(Task... tasks) {
        this.workflow.setTasks(new HashSet<>());
        for (Task task : tasks) {
            workflow.getTasks().add(task);
        }

        return this.workflow;
    }

}
