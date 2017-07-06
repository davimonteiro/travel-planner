package br.uece.beethoven.engine.dsl;


import br.uece.beethoven.logic.dsl.EventHandler;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Data
@ToString
public class Workflow {

    private String name;

    private Set<Task> tasks;

    private Set<EventHandler> eventHandlers;


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
