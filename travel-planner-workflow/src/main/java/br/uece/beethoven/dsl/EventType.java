package br.uece.beethoven.dsl;

public enum EventType {

    // Task events
    TASK_STARTED, TASK_COMPLETED, TASK_TIMEDOUT, TASK_FAILED,

    // Workflow events
    WORKFLOW_SCHEDULED, WORKFLOW_STARTED, WORKFLOW_COMPLETED

}