package br.uece.beethoven.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

public class Counter extends AbstractLoggingActor {

    private int counter = 0;

    static class Message { }

    @Override
    public Receive createReceive() {
        Receive receive = ReceiveBuilder.create()
                .match(Message.class, this::onMessage)
                .build();

        return receive;
    }

    private void onMessage(Message message) {
        counter++;
        log().info("Increased counter " + counter);

        getContext().findChild("").ifPresent(actor -> {
            //actor.forward();
        });
    }

    public static Props props() {
        return Props.create(Counter.class);
    }


    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sample1");
        ActorRef counter = system.actorOf(Counter.props(), "counter");
        counter.tell(new Counter.Message(), ActorRef.noSender());
    }

}
