package br.uece.beethoven.engine.core;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import br.uece.beethoven.engine.dsl.EventType;
import br.uece.beethoven.logic.dsl.Command;
import br.uece.beethoven.logic.dsl.Condition;
import br.uece.beethoven.logic.dsl.EventHandler;
import br.uece.beethoven.repository.EventDslRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("DeciderActor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DeciderActor extends AbstractLoggingActor {

    @Autowired
    private EventDslRepository eventDslRepository;

    @Autowired
    private ActorSystem actorSystem;

    @Override
    public Receive createReceive() {
        Receive receive = ReceiveBuilder.create()

                .match(TaskStartedEvent.class, this::onTaskStartedEvent)
                .match(TaskFailedEvent.class, this::onTaskFailedEvent)
                .match(TaskCompletedEvent.class, this::onTaskCompletedEvent)
                .match(TaskTimeoutEvent.class, this::onTaskTimeoutEvent)

                .match(WorkflowScheduledEvent.class, this::onWorkflowScheduledEvent)
                .match(WorkflowStartedEvent.class, this::onWorkflowStartedEvent)
                .match(WorkflowCompletedEvent.class, this::onWorkflowCompletedEvent)

                /*.match(WorkflowFailedEvent.class, this::onWorkflowFailedEvent)
                .match(WorkflowCanceledEvent.class, this::onWorkflowCanceledEvent)
                .match(WorkflowFailedEvent.class, this::onWorkflowFailedEvent)*/

                .build();


        return receive;
    }

    private void onWorkflowScheduledEvent(WorkflowScheduledEvent workflowScheduledEvent) {
        log().debug("onWorkflowScheduledEvent: " + workflowScheduledEvent);

        actorSystem.actorSelection(ActorPath.WORKFLOW_ACTOR)
                .tell(new WorkflowActor.StartWorkflowCommand(workflowScheduledEvent.workflowName), ActorRef.noSender());

        List<EventHandler> events = eventDslRepository.find(workflowScheduledEvent.workflowName, EventType.WORKFLOW_SCHEDULED);

        for (EventHandler event : events) {
            event.getConditions().forEach(condition -> condition.setActualValues(workflowScheduledEvent));
            event.getCommands().forEach(command -> command.setWorkflowName(workflowScheduledEvent.workflowName));
            evaluateConditionsAndSendCommands(event.getConditions(), event.getCommands(), null);
        }

    }

    private void onWorkflowCompletedEvent(WorkflowCompletedEvent workflowCompleted) {
        log().debug("onWorkflowCompletedEvent: " + workflowCompleted);
        List<EventHandler> events = eventDslRepository.find(workflowCompleted.workflowName, EventType.WORKFLOW_COMPLETED);

        for (EventHandler event : events) {
            event.getConditions().forEach(condition -> condition.setActualValues(workflowCompleted));
            event.getCommands().forEach(command -> command.setWorkflowName(workflowCompleted.workflowName));
            evaluateConditionsAndSendCommands(event.getConditions(), event.getCommands(), workflowCompleted.instanceName);
        }
    }

    private void onWorkflowStartedEvent(WorkflowStartedEvent workflowStartedEvent) {
        log().debug("onWorkflowStartedEvent: " + workflowStartedEvent);
        List<EventHandler> events = eventDslRepository.find(workflowStartedEvent.workflowName, EventType.WORKFLOW_STARTED);

        for (EventHandler event : events) {
            event.getConditions().forEach(condition -> condition.setActualValues(workflowStartedEvent));
            event.getCommands().forEach(command -> command.setWorkflowName(workflowStartedEvent.workflowName));
            evaluateConditionsAndSendCommands(event.getConditions(), event.getCommands(), workflowStartedEvent.instanceName);
        }
    }

    private void onTaskStartedEvent(TaskStartedEvent taskStartedEvent) {
        log().debug("onTaskStartedEvent: " + taskStartedEvent);
        List<EventHandler> events = eventDslRepository.find(taskStartedEvent.workflowName, EventType.TASK_STARTED);

        for (EventHandler event : events) {
            event.getConditions().forEach(condition -> condition.setActualValues(taskStartedEvent));
            event.getCommands().forEach(command -> command.setWorkflowName(taskStartedEvent.workflowName));
            evaluateConditionsAndSendCommands(event.getConditions(), event.getCommands(), taskStartedEvent.instanceName);
        }

    }

    private void onTaskCompletedEvent(TaskCompletedEvent taskCompletedEvent) {
        log().debug("onTaskCompletedEvent: " + taskCompletedEvent);
        List<EventHandler> events = eventDslRepository.find(taskCompletedEvent.workflowName, EventType.TASK_COMPLETED);

        for (EventHandler event : events) {
            event.getConditions().forEach(condition -> condition.setActualValues(taskCompletedEvent));
            event.getCommands().forEach(command -> command.setWorkflowName(taskCompletedEvent.workflowName));
            evaluateConditionsAndSendCommands(event.getConditions(), event.getCommands(), taskCompletedEvent.instanceName);
        }

    }

    private void onTaskFailedEvent(TaskFailedEvent taskFailedEvent) {
        log().debug("onTaskFailedEvent: " + taskFailedEvent);
        List<EventHandler> events = eventDslRepository.find(taskFailedEvent.workflowName, EventType.TASK_FAILED);

        List<Condition> conditions = getConditions(events);
        conditions.forEach(condition -> condition.setActualValues(taskFailedEvent));
        List<Command> commands = getCommands(events);
        commands.forEach(command -> command.setWorkflowName(taskFailedEvent.workflowName));

        evaluateConditionsAndSendCommands(conditions, commands, taskFailedEvent.instanceName);
    }

    private void onTaskTimeoutEvent(TaskTimeoutEvent taskTimeoutEvent) {
        log().debug("onTaskTimeoutEvent: " + taskTimeoutEvent);
        List<EventHandler> events = eventDslRepository.find(taskTimeoutEvent.workflowName, EventType.TASK_TIMEDOUT);

        for (EventHandler event : events) {
            event.getConditions().forEach(condition -> condition.setActualValues(taskTimeoutEvent));
            event.getCommands().forEach(command -> command.setWorkflowName(taskTimeoutEvent.workflowName));
            evaluateConditionsAndSendCommands(event.getConditions(), event.getCommands(), taskTimeoutEvent.instanceName);
        }
    }


    private List<Condition> getConditions(List<EventHandler> events) {
        List<Condition> conditions = events.stream()
                .flatMap(condition -> condition.getConditions().stream())
                .collect(Collectors.toList());

        return conditions;
    }

    private List<Command> getCommands(List<EventHandler> events) {
        List<Command> commands = events.stream()
                .flatMap(command -> command.getCommands().stream())
                .collect(Collectors.toList());

        return commands;
    }

    private void evaluateConditionsAndSendCommands(List<Condition> conditions, List<Command> commands, String instanceName) {
        // If the conditions are true, then
        if (evaluateConditions(conditions)) {
            sendCommands(commands, instanceName);
        }
    }

    private Boolean evaluateConditions(List<Condition> conditions) {
        Boolean result = Boolean.TRUE;
        for (Condition condition : conditions) {
            result = result && condition.eval();
        }

        return result;
    }

    private void sendCommands(List<Command> commands, String instanceName) {
        for(Command command : commands) {
            sendCommand(command, instanceName);
        }
    }

    private void sendCommand(Command command, String instanceName) {
        switch (command.getOperation()) {
            case START_TASK:
                actorSystem.actorSelection(ActorPath.TASK_ACTOR)
                        .tell(new TaskActor.StartTaskCommand(command.getTaskName(),
                                        command.getWorkflowName(),
                                        command.getInput(),
                                        instanceName),
                                ActorRef.noSender());
                break;
            case START_WORKFLOW:
                actorSystem.actorSelection(ActorPath.WORKFLOW_ACTOR)
                        .tell(new WorkflowActor.StartWorkflowCommand(command.getWorkflowName()), ActorRef.noSender());
                break;
            case STOP_WORKFLOW:

                break;
            case CANCEL_WORKFLOW:
                break;

        }

    }


    /**
     * ****************************************************************************
     * <p/>
     * Task Events: TASK_STARTED, TASK_COMPLETED, TASK_TIMEDOUT, TASK_FAILED
     * <p/>
     * *****************************************************************************
     */
    @Data @AllArgsConstructor
    public static class TaskStartedEvent {
        private String taskName;
        private String input;
        private String instanceName;
        private String workflowName;
    }

    @Data @AllArgsConstructor
    public static class TaskCompletedEvent {
        private String taskName;
        private String output;
        private String instanceName;
        private String workflowName;
    }

    @Data @AllArgsConstructor
    public static class TaskTimeoutEvent {
        private String taskName;
        private String instanceName;
        private String workflowName;
    }

    @Data @AllArgsConstructor
    public static class TaskFailedEvent {
        private String taskName;
        private String instanceName;
        private String workflowName;
    }
    /*******************************************************************************/


    /**
     * ****************************************************************************
     * <p/>
     * Workflow Events: WORKFLOW_SCHEDULED, WORKFLOW_STARTED, WORKFLOW_COMPLETED
     * <p/>
     * *****************************************************************************
     */
    @Data @AllArgsConstructor
    public static class WorkflowScheduledEvent {
        private String workflowName;
    }

    @Data @AllArgsConstructor
    public static class WorkflowStartedEvent {
        private String workflowName;
        private String instanceName;
    }

    @Data @AllArgsConstructor
    public static class WorkflowCompletedEvent {
        private String workflowName;
        private String instanceName;
    }

    @Data @AllArgsConstructor
    public static class WorkflowFailedEvent {
        private String workflowName;
        private String instanceName;
    }

    @Data @AllArgsConstructor
    public static class WorkflowCanceledEvent {
        private String workflowName;
        private String instanceName;
    }

    /*******************************************************************************/

}
