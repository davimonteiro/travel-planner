/*package br.uece.beethoven.logic;


import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import br.uece.beethoven.engine.core.TaskActor;
import br.uece.beethoven.engine.WorkflowInstance;
import br.uece.beethoven.engine.dsl.EventType;
import br.uece.beethoven.engine.dsl.Workflow;
import br.uece.beethoven.logic.dsl.Command;
import br.uece.beethoven.repository.EventDslRepository;
import br.uece.beethoven.repository.WorkflowInstanceRepository;
import br.uece.beethoven.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("WorkflowSupervisorActor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WorkflowSupervisorActor extends AbstractLoggingActor {

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private WorkflowInstanceRepository workflowInstanceRepository;

    @Autowired
    private EventDslRepository eventDslRepository;

    @Override
    public Receive createReceive() {
        Receive receive = ReceiveBuilder.create()
                .match(ScheduleWorkflow.class, this::onScheduled)
                .build();

        return receive;
    }

    private void onScheduled(ScheduleWorkflow scheduleWorkflow) {
        // TODO Create a new workflow instance and save it
        Workflow workflow = workflowRepository.findByName(scheduleWorkflow.workflowName);
        WorkflowInstance workflowInstance = new WorkflowInstance(workflow);
        workflowInstance.setInstanceId(UUID.randomUUID().toString());
        workflowInstanceRepository.save(workflowInstance);


        // TODO Procurar eventos on(Workflow.WorkflowEvent.WORKFLOW_SCHEDULED), nos quais suas condições sejam verdadeiras
        // TODO Se forem, disparar um comando
        eventDslRepository.getEvents().stream()
                .filter(e -> e.getEventType().equals(EventType.WORKFLOW_SCHEDULED))
                .filter(e -> e.getConditions().stream().anyMatch(c -> c.eval(scheduleWorkflow.workflowName)))
                .forEach(e -> e.getCommands().forEach(command -> publish(command)));

        log().info("A new instance has been created!");
        //getContext().getSystem().actorOf(WorkflowInstanceActor.props(), workflowInstance.getInstanceId());
    }

    public void publish(Command command) {
        if (command.getOperation().equals(Command.CommandOperation.START_TASK)) {
            ActorSystem system = ActorSystem.create("system");
            ActorRef task = system.actorOf(TaskActor.props(), "task");
            task.tell(new TaskActor.StartTask(command.getTaskName()), ActorRef.noSender());
        }
        System.err.println(command);
    }

    public static class ScheduleWorkflow {

        private String workflowName;

        public ScheduleWorkflow(String workflowName) {
            this.workflowName = workflowName;
        }
    }

    public static Props props() {
        return Props.create(WorkflowSupervisorActor.class);
    }

}*/
