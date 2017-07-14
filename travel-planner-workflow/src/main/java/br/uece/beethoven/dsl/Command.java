package br.uece.beethoven.dsl;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Command {

    private CommandOperation operation;

    private String taskName;

    private String workflowName;

    private String input;

    public enum CommandOperation {
        START_TASK, START_WORKFLOW, STOP_WORKFLOW, CANCEL_WORKFLOW;
    }

}
