package br.uece.beethoven.logic.dsl;


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
        condition.setWorkflowName(name);
        condition.setOperator(Condition.Operator.WORKFLOW_NAME_EQUALS_TO);
        return this.condition;
    }


    public Condition taskNameEqualsTo(String name) {
        condition.setTaskName(name);
        condition.setOperator(Condition.Operator.TASK_NAME_EQUALS_TO);
        return this.condition;
    }

    public Condition taskResponseEqualsTo(String jsonPath, Matcher matcher) {
        condition.setJsonPath(jsonPath);
        condition.setMatcher(matcher);
        condition.setOperator(Condition.Operator.TASK_RESPONSE_EQUALS_TO);
        return this.condition;
    }


}
