package br.uece.beethoven.engine;

import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Data
@ToString
public class WorkflowInstance {
    private String instanceName;
    private String workflowName;
    private Map<String, TaskInstance> tasks = new HashMap<>();
    private WorkflowStatus status;
    private Long executionTime;

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
