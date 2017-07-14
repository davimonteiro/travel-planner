package br.uece.beethoven.dsl;

import br.uece.beethoven.engine.core.DeciderActor;
import com.jayway.jsonpath.JsonPath;
import lombok.Data;
import org.hamcrest.Matcher;

@Data
public class Condition {

    /** Expected conditions */
    private String taskName;
    private String workflowName;
    private Operator operator;
    private String jsonPath;
    private Matcher matcher;


    /** Actual values */
    private String currentTaskName;
    private String currentWorkflowName;
    private String currentResponse;


    public void setActualValues(DeciderActor.WorkflowScheduledEvent workflowScheduledEvent) {
        this.currentWorkflowName = workflowScheduledEvent.getWorkflowName();
    }

    public void setActualValues(DeciderActor.WorkflowStartedEvent workflowStartedEvent) {
        this.currentWorkflowName = workflowStartedEvent.getWorkflowName();
    }

    public void setActualValues(DeciderActor.WorkflowCompletedEvent workflowCompleted) {
        this.currentWorkflowName = workflowCompleted.getWorkflowName();
    }

    public void setActualValues(DeciderActor.TaskStartedEvent taskStartedEvent) {
        this.currentWorkflowName = taskStartedEvent.getWorkflowName();
        this.currentTaskName = taskStartedEvent.getTaskName();
    }

    public void setActualValues(DeciderActor.TaskCompletedEvent taskCompletedEvent) {
        this.currentWorkflowName = taskCompletedEvent.getWorkflowName();
        this.currentTaskName = taskCompletedEvent.getTaskName();
    }

    public void setActualValues(DeciderActor.TaskFailedEvent taskFailedEvent) {
        this.currentWorkflowName = taskFailedEvent.getWorkflowName();
        this.currentTaskName = taskFailedEvent.getTaskName();
    }

    public void setActualValues(DeciderActor.TaskTimeoutEvent taskTimeoutEvent) {
        this.currentWorkflowName = taskTimeoutEvent.getWorkflowName();
        this.currentTaskName = taskTimeoutEvent.getTaskName();
    }


    public Boolean eval() {
        Boolean result = Boolean.FALSE;

        switch (operator) {
            case TASK_NAME_EQUALS_TO:
                result = taskName.equals(currentTaskName);
                break;
            case WORKFLOW_NAME_EQUALS_TO:
                result = workflowName.equals(currentWorkflowName);
                break;
            case TASK_RESPONSE_EQUALS_TO:
                String path = JsonPath.compile(currentResponse).getPath();
                result = matcher.matches(path);
                break;
        }

        return result;
    }

    public Boolean eval(String value) {
        Boolean result = Boolean.FALSE;

        switch (operator) {
            case TASK_NAME_EQUALS_TO:
                result = taskName.equals(value);
                break;
            case WORKFLOW_NAME_EQUALS_TO:
                result = workflowName.equals(value);
                break;
            case TASK_RESPONSE_EQUALS_TO:
                String path = JsonPath.compile(value).getPath();
                result = matcher.matches(path);
                break;
        }

        return result;
    }

    public enum Operator {
        TASK_NAME_EQUALS_TO, WORKFLOW_NAME_EQUALS_TO, TASK_RESPONSE_EQUALS_TO
    }

}
