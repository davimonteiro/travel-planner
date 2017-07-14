package br.uece.beethoven.engine.core.support;


import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import br.uece.beethoven.engine.WorkflowInstance;

public class WorkflowInstanceActor extends AbstractLoggingActor {

    private WorkflowInstance workflowInstance;

    @Override
    public Receive createReceive() {
        return null;
    }

    public static Props props() {
        return Props.create(WorkflowInstanceActor.class);
    }

}
