package br.uece.beethoven.service;


import akka.actor.ActorSystem;
import br.uece.beethoven.dsl.HttpRequest;
import br.uece.beethoven.dsl.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.Arrays;

@Service
public class TaskService {

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @Autowired
    private ActorSystem actorSystem;

    @Async
    public void start(Task task, String input, String instanceId) {
        HttpRequest httpRequest = task.getHttpRequest();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity entity = new HttpEntity(httpRequest.getBody(), headers);
        ListenableFuture<ResponseEntity<String>> future = asyncRestTemplate.exchange(httpRequest.getUrl(), httpRequest.getMethod(), entity, String.class);

        // TODO Publish TASK_STARTED
        System.out.println("TASK_STARTED - " + task.getName());

        task.setStatus(Task.TaskStatus.IN_PROGRESS);
        future.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
            @Override
            public void onSuccess(ResponseEntity<String> responseEntity) {
                // TODO Publish TASK_COMPLETED
                System.out.println("TASK_COMPLETED - " + task.getName());
                System.err.println(responseEntity.getBody());
                task.setResponse(responseEntity.getBody());
                task.setStatus(Task.TaskStatus.COMPLETED);
                //actorSystem.actorSelection("/user/EventActor").tell(new DeciderActor.FiredEvent(instanceId, EventType.TASK_STARTED, task.getName()), ActorRef.noSender());
            }

            @Override
            public void onFailure(Throwable throwable) {
                // TODO Publish TASK_FAILED
                System.out.println("TASK_FAILED - " + task.getName());
                task.setStatus(Task.TaskStatus.FAILED);
            }
        });

    }

}
