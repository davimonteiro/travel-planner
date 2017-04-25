package br.uece.beethoven.logic.dsl;

import br.uece.beethoven.engine.Task;
import br.uece.beethoven.engine.Workflow;
import lombok.Data;

@Data
public class Condition {

    private Task task;

    private Workflow workflow;

    private Boolean result;

}
