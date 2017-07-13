package br.uece.beethoven.engine.core;


import akka.actor.AbstractLoggingActor;
import akka.japi.pf.ReceiveBuilder;
import br.uece.beethoven.engine.MetricRegistryUtils;
import br.uece.beethoven.engine.TaskInstance;
import br.uece.beethoven.engine.WorkflowInstance;
import br.uece.beethoven.engine.dsl.Workflow;
import br.uece.beethoven.repository.WorkflowRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("ReportActor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReportActor extends AbstractLoggingActor {

    private List<ReportWorkflowScheduledEvent> scheduledWorkflows = new ArrayList<>();
    private Map<String, WorkflowInstance> workflowInstances = new HashMap<>();

    @Autowired
    private WorkflowRepository workflowRepository;

    @Override
    public Receive createReceive() {

        Receive receive = ReceiveBuilder.create()
                .match(ReportWorkflowScheduledEvent.class, this::onReportWorkflowScheduledEvent)
                .match(ReportWorkflowFailedEvent.class, this::onReportWorkflowFailedEvent)
                .match(ReportWorkflowCanceledEvent.class, this::onReportWorkflowCanceledEvent)
                //.match(ReportWorkflowStartedEvent.class, this::onReportWorkflowStartedEvent)

                .match(ReportTaskStartedEvent.class, this::onReportTaskStartedEvent)
                .match(ReportTaskCompletedEvent.class, this::onReportTaskCompletedEvent)

                .build();

        return receive;
    }

    private void onReportTaskStartedEvent(ReportTaskStartedEvent reportTaskStartedEvent) {
        boolean existeInstancia = workflowInstances.containsKey(reportTaskStartedEvent.workflowInstanceName);
        if (!existeInstancia) {
            WorkflowInstance workflowInstance = new WorkflowInstance();
            workflowInstance.setWorkflowName(reportTaskStartedEvent.workflowName);
            workflowInstance.setInstanceName(reportTaskStartedEvent.workflowInstanceName);
            workflowInstances.put(reportTaskStartedEvent.workflowInstanceName, workflowInstance);
        }

        TaskInstance taskInstance = new TaskInstance();
        taskInstance.setTaskInstanceName(reportTaskStartedEvent.taskInstanceName);
        taskInstance.setTaskName(reportTaskStartedEvent.taskName);
        taskInstance.setTimer(MetricRegistryUtils.getInstance().timer(reportTaskStartedEvent.taskInstanceName));
        taskInstance.getTimer().time();
        workflowInstances.get(reportTaskStartedEvent.workflowInstanceName).getTasks().put(taskInstance.getTaskInstanceName(), taskInstance);
    }

    private void onReportTaskCompletedEvent(ReportTaskCompletedEvent reportTaskCompletedEvent) {


        Map<String, TaskInstance> tasks = workflowInstances.get(reportTaskCompletedEvent.workflowInstanceName).getTasks();
        if (tasks != null && !tasks.isEmpty()) {
            TaskInstance taskInstance = tasks.get(reportTaskCompletedEvent.taskInstanceName);
            if (taskInstance != null) {
                long stop = taskInstance.getTimer().time().stop();
                taskInstance.setExecutionTime(stop);
            }

        }

        if (isCompleted(reportTaskCompletedEvent.workflowName, reportTaskCompletedEvent.workflowInstanceName)) {
            workflowInstances.values().forEach(instance -> {

                /*long total = 0;
                for (TaskInstance taskInstance : instance.getTasks().values()) {
                    total += taskInstance.getExecutionTime();
                }*/

                long total = instance.getTasks().values().stream().mapToLong(TaskInstance::getExecutionTime).sum();
                instance.setExecutionTime(total);

                System.err.println("Workflow name: " + instance.getWorkflowName());
                System.err.println("Instance name: " + instance.getInstanceName());
                System.err.println("Execution time: " + instance.getExecutionTime());

            });

        }
    }

    private boolean isCompleted(String workflowName, String workflowInstanceName) {
        Workflow workflow = workflowRepository.findByName(workflowName);
        return workflowInstances.get(workflowInstanceName).getTasks().size() == workflow.getTasks().size();
    }

    private void onReportWorkflowScheduledEvent(ReportWorkflowScheduledEvent reportWorkflowScheduledEvent) {
        // Gravar a quantidade de scheduled workflows
        scheduledWorkflows.add(reportWorkflowScheduledEvent);
    }

    private void onReportWorkflowFailedEvent(ReportWorkflowFailedEvent reportWorkflowFailedEvent) {
        log().debug("onReportWorkflowFailedEvent: " + reportWorkflowFailedEvent);
    }

    private void onReportWorkflowCanceledEvent(ReportWorkflowCanceledEvent workflowCanceledEvent) {
        log().debug("onReportWorkflowCanceledEvent: " + workflowCanceledEvent);
    }


    /* Workflow */
    @Data @AllArgsConstructor
    public static class ReportWorkflowStartedEvent {
        private String workflowName;
        private String instanceName;
    }

    @Data @AllArgsConstructor
    public static class ReportWorkflowCompletedEvent {
        private String workflowName;
        private String instanceName;
    }

    @Data
    @AllArgsConstructor
    public static class ReportWorkflowScheduledEvent {
        private String workflowName;
    }

    @Data @AllArgsConstructor
    public static class ReportWorkflowFailedEvent {
        private String workflowName;
        private String instanceName;
    }

    @Data @AllArgsConstructor
    public static class ReportWorkflowCanceledEvent {
        private String workflowName;
        private String instanceName;
    }

    /* Tasks */

    @Data @AllArgsConstructor
    public static class ReportTaskStartedEvent {
        private String taskName;
        private String taskInstanceName;
        private String workflowInstanceName;
        private String workflowName;
    }

    @Data @AllArgsConstructor
    public static class ReportTaskCompletedEvent {
        private String taskName;
        private String taskInstanceName;
        private String workflowInstanceName;
        private String workflowName;
    }

    @Data @AllArgsConstructor
    public static class ReportTaskTimeoutEvent {
        private String taskName;
        private String taskInstanceName;
        private String workflowInstanceName;
        private String workflowName;
    }

    @Data @AllArgsConstructor
    public static class ReportTaskFailedEvent {
        private String taskName;
        private String taskInstanceName;
        private String workflowInstanceName;
        private String workflowName;
    }

}
