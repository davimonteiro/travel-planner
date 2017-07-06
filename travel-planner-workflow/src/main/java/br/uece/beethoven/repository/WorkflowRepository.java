package br.uece.beethoven.repository;


import br.uece.beethoven.engine.dsl.Workflow;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class WorkflowRepository {

    private Map<String, Workflow> workflowMap = new ConcurrentHashMap<>();

    public void save(Workflow workflow) {
        workflowMap.put(workflow.getName(), workflow);
    }

    public void saveAll(Workflow ... workflows) {
        Arrays.asList(workflows).stream().forEach(w -> workflowMap.put(w.getName(), w));
    }

    public Workflow findByName(String name) {
        return workflowMap.get(name);
    }

    public void delete(String name) {
        workflowMap.remove(name);
    }

    /*private static final String KEY = "Workflow";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations hashOps;

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    public void save(Workflow workflow) {
        hashOps.put(KEY, workflow.getName(), workflow);
    }

    public Workflow findByName(String name) {
        return (Workflow) hashOps.get(KEY, name);
    }

    public Map<Object, Object> findAll() {
        return hashOps.entries(KEY);
    }

    public void delete(String name) {
        hashOps.delete(KEY, name);
    }*/

}
