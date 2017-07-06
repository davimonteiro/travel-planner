package br.uece.beethoven.engine.core;


import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.japi.pf.ReceiveBuilder;
import br.uece.beethoven.repository.TaskRepository;
import br.uece.beethoven.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import scala.concurrent.duration.Duration;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component("TaskActor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskActor extends AbstractLoggingActor {

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Receive createReceive() {
        Receive receive = ReceiveBuilder.create()
                .match(StartTaskCommand.class, this::onStartTaskCommand)
                .build();

        return receive;
    }

    private void onStartTaskCommand(StartTaskCommand startTaskCommand) {
        //Task task = taskRepository.findByName(startTaskCommand.taskName);
        //taskService.start(task, startTaskCommand.input, startTaskCommand.instanceName);

        log().debug("onStartTaskCommand: " + startTaskCommand);

        String taskInstanceName = UUID.randomUUID().toString();
        actorSystem.actorSelection(ActorPath.DECIDER_ACTOR)
                .tell(new DeciderActor.TaskStartedEvent(startTaskCommand.taskName,
                        startTaskCommand.input,
                        startTaskCommand.workflowInstanceName,
                        startTaskCommand.workflowName), ActorRef.noSender());

        actorSystem.actorSelection(ActorPath.REPORT_ACTOR)
                .tell(new ReportActor.ReportTaskStartedEvent(startTaskCommand.taskName,
                        taskInstanceName,
                        startTaskCommand.workflowInstanceName,
                        startTaskCommand.workflowName), ActorRef.noSender());



        // Some minutes later
        actorSystem.scheduler().scheduleOnce(Duration.create(5, TimeUnit.SECONDS), () -> {
            String output = "";
            actorSystem.actorSelection(ActorPath.DECIDER_ACTOR)
                    .tell(new DeciderActor.TaskCompletedEvent(startTaskCommand.taskName,
                            output,
                            startTaskCommand.workflowInstanceName,
                            startTaskCommand.workflowName), ActorRef.noSender());

            actorSystem.actorSelection(ActorPath.REPORT_ACTOR)
                    .tell(new ReportActor.ReportTaskCompletedEvent(startTaskCommand.taskName,
                            taskInstanceName,
                            startTaskCommand.workflowInstanceName,
                            startTaskCommand.workflowName), ActorRef.noSender());

        }, actorSystem.dispatcher());

    }


    /**
     * ****************************************************************************
     * <p/>
     * Task Commands: START_TASK
     * <p/>
     * *****************************************************************************
     */
    @Data @AllArgsConstructor
    public static class StartTaskCommand {
        private String taskName;
        private String workflowName;
        private String input;
        private String workflowInstanceName;
    }

    /*******************************************************************************/

}
