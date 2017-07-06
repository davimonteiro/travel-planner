package br.uece.beethoven.engine.core;


import akka.actor.AbstractLoggingActor;
import akka.japi.pf.ReceiveBuilder;
import br.uece.beethoven.engine.WorkflowInstance;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("ReportActor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReportActor extends AbstractLoggingActor {


    // TODO Devo gravar o tempo de execução de cada workflow instance
    // TODO Devo gravar o tempo de execução de cada task instance

    private List<ReportWorkflowScheduledEvent> scheduledWorkflows = new ArrayList<>();

    private List<WorkflowInstance> workflowInstances = new ArrayList<>();



    @Override
    public Receive createReceive() {

        Receive receive = ReceiveBuilder.create()
                .match(ReportWorkflowScheduledEvent.class, this::onReportWorkflowScheduledEvent)
                .match(ReportWorkflowFailedEvent.class, this::onReportWorkflowFailedEvent)
                .match(ReportWorkflowCanceledEvent.class, this::onReportWorkflowCanceledEvent)

                .match(ReportTaskStartedEvent.class, this::onReportTaskStartedEvent)

                .build();

        return receive;
    }

    private void onReportTaskStartedEvent(ReportTaskStartedEvent reportTaskStartedEvent) {

        
        WorkflowInstance workflowInstance = new WorkflowInstance();
        workflowInstance.setWorkflowName(reportTaskStartedEvent.workflowName);
        workflowInstance.setInstanceName(reportTaskStartedEvent.instanceName);


    }

    private void onReportWorkflowScheduledEvent(ReportWorkflowScheduledEvent reportWorkflowScheduledEvent) {
        scheduledWorkflows.add(reportWorkflowScheduledEvent);
    }

    private void onReportWorkflowFailedEvent(ReportWorkflowFailedEvent reportWorkflowFailedEvent) {
        log().debug("onReportWorkflowFailedEvent: " + reportWorkflowFailedEvent);
    }

    private void onReportWorkflowCanceledEvent(ReportWorkflowCanceledEvent workflowCanceledEvent) {
        log().debug("onReportWorkflowCanceledEvent: " + workflowCanceledEvent);
    }



    @Data @AllArgsConstructor
    public static class ReportTaskStartedEvent {
        private String taskName;
        private String input;
        private String instanceName;
        private String workflowName;
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


}
