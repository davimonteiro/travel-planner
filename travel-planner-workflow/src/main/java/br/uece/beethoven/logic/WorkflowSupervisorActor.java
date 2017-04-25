package br.uece.beethoven.logic;


import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

public class WorkflowSupervisorActor extends AbstractLoggingActor {


    @Override
    public Receive createReceive() {
        Receive receive = ReceiveBuilder.create()
                .match(WorkflowInstance.class, this::createNewWorkflowInstance)
                .build();

        return receive;
    }

    private void createNewWorkflowInstance(WorkflowInstance instance) {
        log().info(" new instance has been created!");
        getContext().getSystem().actorOf(WorkflowInstanceActor.props(), "counter");
   }

    static class WorkflowInstance {

        private String workflowName;

        public WorkflowInstance(String workflowName) {
            this.workflowName = workflowName;
        }
    }

    public static Props props() {
        return Props.create(WorkflowSupervisorActor.class);
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sample1");
        ActorRef supervisor = system.actorOf(WorkflowSupervisorActor.props(), "WorkflowSupervisor");
        supervisor.tell(new WorkflowSupervisorActor.WorkflowInstance("workflow1"), ActorRef.noSender());
    }

}
