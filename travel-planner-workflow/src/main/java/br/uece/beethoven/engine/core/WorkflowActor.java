package br.uece.beethoven.engine.core;


import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.japi.pf.ReceiveBuilder;
import br.uece.beethoven.engine.WorkflowInstance;
import br.uece.beethoven.engine.core.support.WorkflowInstanceActor;
import br.uece.beethoven.repository.WorkflowInstanceRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component("WorkflowActor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WorkflowActor extends AbstractLoggingActor {

    private static final String DECIDER_ACTOR = "/user/DeciderActor";

    @Autowired
    private WorkflowInstanceRepository workflowInstanceRepository;

    @Autowired
    private ActorSystem actorSystem;

    @Override
    public Receive createReceive() {
        Receive receive = ReceiveBuilder.create()
                .match(ScheduleWorkflowCommand.class, this::onScheduleWorkflowCommand)
                .match(StartWorkflowCommand.class, this::onStartWorkflowCommand)
                .match(StopWorkflowCommand.class, this::onStopWorkflowCommand)
                .match(CancelWorkflowCommand.class, this::onCancelWorkflowCommand)
                .build();

        return receive;
    }

    private void onScheduleWorkflowCommand(ScheduleWorkflowCommand scheduleWorkflowCommand) {
        log().debug("onScheduleWorkflowCommand: " + scheduleWorkflowCommand);
        sendEvent(new DeciderActor.WorkflowScheduledEvent(scheduleWorkflowCommand.workflowName));
    }

    private void onStartWorkflowCommand(StartWorkflowCommand startWorkflowCommand) {
        log().debug("onStartWorkflowCommand: " + startWorkflowCommand);

        WorkflowInstance workflowInstance = createWorkflowInstance(startWorkflowCommand);
        getContext().actorOf(WorkflowInstanceActor.props(), workflowInstance.getInstanceName());

        sendEvent(new DeciderActor.WorkflowStartedEvent(startWorkflowCommand.workflowName, workflowInstance.getInstanceName()));
    }

    private void onStopWorkflowCommand(StopWorkflowCommand stopWorkflowCommand) {
        log().debug("onStopWorkflowCommand: " + stopWorkflowCommand);
        sendEvent(new DeciderActor.WorkflowStoppedEvent(stopWorkflowCommand.workflowName, stopWorkflowCommand.instanceName));
    }

    private void onCancelWorkflowCommand(CancelWorkflowCommand cancelWorkflowCommand) {
        log().debug("onCancelWorkflowCommand: " + cancelWorkflowCommand);
        sendEvent(new DeciderActor.WorkflowCanceledEvent(cancelWorkflowCommand.workflowName, cancelWorkflowCommand.instanceName));
    }


    private WorkflowInstance createWorkflowInstance(StartWorkflowCommand startWorkflowCommand) {
        WorkflowInstance workflowInstance = new WorkflowInstance();
        workflowInstance.setInstanceName(UUID.randomUUID().toString());
        workflowInstance.setWorkflowName(startWorkflowCommand.workflowName);
        workflowInstanceRepository.save(workflowInstance);

        return workflowInstance;
    }

    private void sendEvent(DeciderActor.WorkflowEvent workflowEvent) {
        actorSystem.actorSelection(DECIDER_ACTOR).tell(workflowEvent, ActorRef.noSender());
    }


    /*******************************************************************************
     *
     * Workflow Commands: SCHEDULE_WORKFLOW, START_WORKFLOW,
     *                    STOP_WORKFLOW, CANCEL_WORKFLOW
     *
     *******************************************************************************/
    @Data @AllArgsConstructor
    public static class ScheduleWorkflowCommand {
        private String workflowName;
    }

    @Data @AllArgsConstructor
    public static class StartWorkflowCommand {
        private String workflowName;
    }

    @Data @AllArgsConstructor
    public static class StopWorkflowCommand {
        private String workflowName;
        private String instanceName;
    }

    @Data @AllArgsConstructor
    public static class CancelWorkflowCommand {
        private String workflowName;
        private String instanceName;
    }
    /*******************************************************************************/

}
