package br.uece.beethoven.repository;


import br.uece.beethoven.engine.WorkflowInstance;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class WorkflowInstanceRepository {

    private Map<String, WorkflowInstance> instancesMap = new ConcurrentHashMap<>();

    public void save(WorkflowInstance workflowInstance) {
        instancesMap.put(workflowInstance.getInstanceName(), workflowInstance);
    }

    public void saveAll(WorkflowInstance ... instances) {
        Arrays.asList(instances).stream().forEach(wi -> instancesMap.put(wi.getInstanceName(), wi));
    }

    public WorkflowInstance findByName(String name) {
        return instancesMap.get(name);
    }

    public void delete(String name) {
        instancesMap.remove(name);
    }

}
