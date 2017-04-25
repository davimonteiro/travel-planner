package br.uece.beethoven.engine;


import lombok.Data;

import java.util.Set;

@Data
public class Workflow {

    private String name;

    private Set<Task> tasks;

    // https://github.com/aws/aws-sdk-java/blob/master/aws-java-sdk-simpleworkflow/src/main/java/com/amazonaws/services/simpleworkflow/model/EventType.java
    public enum WorkflowEvent {
        WORKFLOW_SCHEDULED, WORKFLOW_STARTED, WORKFLOW_COMPLETED;
    }


    public enum  WorkflowStatus {
        RUNNING(false, false), COMPLETED(true, true),
        FAILED(true, false), TIMED_OUT(true, false),
        TERMINATED(true, false), PAUSED(false, true);

        private boolean terminal;

        private boolean successful;

        WorkflowStatus(boolean terminal, boolean successful) {
            this.terminal = terminal;
            this.successful = successful;
        }

        public boolean isTerminal(){
            return terminal;
        }

        public boolean isSuccessful(){
            return successful;
        }
    }

}
