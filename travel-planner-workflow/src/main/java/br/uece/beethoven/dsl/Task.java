package br.uece.beethoven.dsl;

import lombok.Data;

@Data
public class Task {

    private String name;

    private HttpRequest httpRequest;

    private TaskStatus status;

    private String response;

    private Workflow workflow;

    public enum TaskStatus {

        IN_PROGRESS(false, true, true),
        CANCELED(true, false, false),
        FAILED(true, false, true),
        COMPLETED(true, true, true),
        SCHEDULED(false, true, true),
        TIMED_OUT(true, false, true),
        READY_FOR_RERUN(false, true, true),
        SKIPPED(true, true, false);

        private boolean terminal;

        private boolean successful;

        private boolean retriable;

        TaskStatus(boolean terminal, boolean successful, boolean retriable){
            this.terminal = terminal;
            this.successful = successful;
            this.retriable = retriable;
        }

        public boolean isTerminal(){
            return terminal;
        }

        public boolean isSuccessful(){
            return successful;
        }

        public boolean isRetriable(){
            return retriable;
        }
    }

}
