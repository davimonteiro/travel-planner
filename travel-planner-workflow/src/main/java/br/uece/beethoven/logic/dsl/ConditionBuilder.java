package br.uece.beethoven.logic.dsl;


import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matcher;

public class ConditionBuilder {

    private Condition condition;

    public ConditionBuilder () {
        this.condition = new Condition();
    }

    public static ConditionBuilder condition() {
        return new ConditionBuilder();
    }

    public Condition workflowNameEqualsTo(String name) {
        this.condition.setResult(condition.getWorkflow().getName().equals(name));
        return this.condition;
    }


    public Condition taskNameEqualsTo(String name) {
        this.condition.setResult(condition.getTask().getName().equals(name));
        return this.condition;
    }

    public Condition taskNameNotEqualsTo(String name) {
        this.condition.setResult(!condition.getTask().getName().equals(name));
        return this.condition;
    }

    public Condition taskResponseEqualsTo(String jsonPath, Matcher matcher) {
        String path = JsonPath.compile(this.condition.getTask().getResponse()).getPath();
        this.condition.setResult(matcher.matches(path));
        return this.condition;
    }


}
