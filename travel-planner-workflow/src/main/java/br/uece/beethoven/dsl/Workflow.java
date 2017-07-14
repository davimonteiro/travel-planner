package br.uece.beethoven.dsl;


import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
@ToString
public class Workflow {

    private String name;
    private Set<Task> tasks;
    private Set<EventHandler> eventHandlers;

}
