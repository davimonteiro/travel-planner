package br.uece.beethoven.dsl;

import java.util.Arrays;

public class EventDslBuilder {

    private EventHandler eventHandler;

    public EventDslBuilder() {
        this.eventHandler = new EventHandler();
    }

    public static EventDslBuilder eventDsl() {
        return new EventDslBuilder();
    }

    public EventDslBuilder on(EventType eventType) {
        eventHandler.setEventType(eventType);
        return this;
    }

    public EventDslBuilder when(Condition ... conditions) {
        eventHandler.getConditions().addAll(Arrays.asList(conditions));
        return this;
    }

    public EventHandler then(Command ... commands) {
        eventHandler.getCommands().addAll(Arrays.asList(commands));
        return eventHandler;
    }

}
