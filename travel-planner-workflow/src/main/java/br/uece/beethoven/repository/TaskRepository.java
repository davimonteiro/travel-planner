package br.uece.beethoven.repository;


import br.uece.beethoven.dsl.Task;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TaskRepository {

    private Map<String, Task> taskMap = new ConcurrentHashMap<>();

    public void save(Task task) {
        taskMap.put(task.getName(), task);
    }

    public void save(Collection<Task> tasks) {
        tasks.stream().forEach(t -> taskMap.put(t.getName(), t));
    }

    public void saveAll(Task ... tasks) {
        Arrays.asList(tasks).stream().forEach(t -> taskMap.put(t.getName(), t));
    }

    public Task findByName(String name) {
        return taskMap.get(name);
    }

    public void delete(String name) {
        taskMap.remove(name);
    }

    /*private static final String KEY = "Task";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations hashOps;

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    public void save(Task task) {
        hashOps.put(KEY, task.getName(), task);
    }

    public Task findByName(String name) {
        return (Task) hashOps.get(KEY, name);
    }

    public Map<Object, Object> findAll() {
        return hashOps.entries(KEY);
    }

    public void delete(String name) {
        hashOps.delete(KEY, name);
    }*/

}
