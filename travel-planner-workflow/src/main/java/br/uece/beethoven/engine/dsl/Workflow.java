package br.uece.beethoven.engine.dsl;


import br.uece.beethoven.logic.dsl.EventHandler;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Data
@ToString
public class Workflow {

    private String name;

    private Set<Task> tasks;

    private Set<EventHandler> eventHandlers;

}
