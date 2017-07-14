package br.uece.beethoven.repository;

import br.uece.beethoven.dsl.EventType;
import br.uece.beethoven.dsl.Workflow;
import br.uece.beethoven.dsl.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EventDslRepository {

    @Autowired
    private WorkflowRepository workflowRepository;

    public List<EventHandler> find(String workflowName, EventType eventType) {
        Workflow workflow = workflowRepository.findByName(workflowName);

        List<EventHandler> events = workflow.getEventHandlers().stream()
                .filter(e -> e.getEventType().equals(eventType))
                .collect(Collectors.toList());

        return events;
    }

}
