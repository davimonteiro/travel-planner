package br.uece.beethoven.dsl;


import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Data
@ToString
public class EventHandler {

    private EventType eventType;

    private List<Condition> conditions = new ArrayList<>();

    private List<Command> commands = new ArrayList<>();

}
