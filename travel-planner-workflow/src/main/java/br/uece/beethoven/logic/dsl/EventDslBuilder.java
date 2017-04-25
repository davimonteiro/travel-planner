package br.uece.beethoven.logic.dsl;

import br.uece.beethoven.engine.Task;
import br.uece.beethoven.engine.Workflow;

import java.util.Arrays;

public class EventDslBuilder {

    private EventDsl eventDsl;

    public EventDslBuilder() {
        this.eventDsl = new EventDsl();
    }

    public static EventDslBuilder eventDsl() {
        return new EventDslBuilder();
    }

    public EventDslBuilder on(Task.TaskEvent taskEvent) {
        eventDsl.setTaskEvent(taskEvent);
        return this;
    }

    public EventDslBuilder on(Workflow.WorkflowEvent workflowEvent) {
        eventDsl.setWorkflowEvent(workflowEvent);
        return this;
    }

    public EventDslBuilder when(Condition ... conditions) {
        this.eventDsl.getConditions().addAll(Arrays.asList(conditions));
        return this;
    }

    public EventDsl then(DslAction dslAction) {
        this.eventDsl.setDslAction(dslAction);
        return this.eventDsl;
    }

    // TODO Conditions
    // taskNameEqualsTo('searchHotelsTask')
    // taskNameNotEqualsTo('searchHotelsTask')
    // taskResponseEqualsTo(jsonPath, matchers)

    // TODO DslActions
    // startTask(name)

    // startWorkflow(name)
    // stopWorkflow(name)
    // cancelWorkflow(name)




}
