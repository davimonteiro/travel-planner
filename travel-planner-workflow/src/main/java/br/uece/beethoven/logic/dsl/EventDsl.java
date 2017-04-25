package br.uece.beethoven.logic.dsl;


import br.uece.beethoven.engine.Task;
import br.uece.beethoven.engine.Workflow;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EventDsl {

    private Task.TaskEvent taskEvent;

    private Workflow.WorkflowEvent workflowEvent;

    private List<Condition> conditions = new ArrayList<>();

    private DslAction dslAction;

}
