package br.uece.beethoven.service;

import br.uece.beethoven.dsl.Workflow;
import br.uece.beethoven.engine.WorkflowInstance;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WorkflowService {

    private Map<String, Workflow> workflows = new ConcurrentHashMap<>();
    private Map<String, WorkflowInstance> workflowInstances = new ConcurrentHashMap<>();

    public Workflow findWorkflowByName(String name) {
        return workflows.get(name);
    }

    public WorkflowInstance findWorkflowInstanceById(String id) {
        return workflowInstances.get(id);
    }

    public Workflow save(Workflow workflow) {
        workflows.put(workflow.getName(), workflow);
        return workflow;
    }

    public WorkflowInstance save(WorkflowInstance workflowInstance) {
        workflowInstances.put(workflowInstance.getInstanceName(), workflowInstance);
        return workflowInstance;
    }

}
