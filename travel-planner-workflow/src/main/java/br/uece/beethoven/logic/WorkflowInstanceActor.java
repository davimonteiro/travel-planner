package br.uece.beethoven.logic;


import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import br.uece.beethoven.engine.WorkflowInstance;
import br.uece.beethoven.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;

public class WorkflowInstanceActor extends AbstractLoggingActor {

    @Autowired
    private WorkflowService workflowService;


    private String workflowName;

    @Override
    public Receive createReceive() {
        WorkflowInstance workflowInstance = workflowService.findWorkflowInstanceById(getSelf().path().name());


        return null;
    }

    public static Props props() {
        return Props.create(WorkflowInstanceActor.class);
    }

}
