package br.uece.beethoven.engine;

import com.codahale.metrics.Timer;
import lombok.Data;
import lombok.ToString;

import java.util.Optional;

@Data
@ToString
public class TaskInstance {

    private String taskName;
    private String taskInstanceName;
    private Timer timer;
    private Long executionTime = 0L;

}
