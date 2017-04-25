package br.uece.beethoven.logic;


import akka.actor.AbstractLoggingActor;
import akka.actor.Props;

public class WorkflowInstanceActor extends AbstractLoggingActor {

    private String workflowName;

    @Override
    public Receive createReceive() {
        return null;
    }

    public static Props props() {
        return Props.create(WorkflowInstanceActor.class);
    }

}
