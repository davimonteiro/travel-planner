package br.uece.beethoven.logic.dsl;


public class DslActionBuilder {

    private DslAction dslAction;

    private DslActionBuilder() {
        this.dslAction = new DslAction();
    }

    public static DslAction startTask(String name) {
        DslActionBuilder dslActionBuilder = new DslActionBuilder();
        return dslActionBuilder.dslAction;
    }

    public static DslAction startTask(String name, String body) {
        DslActionBuilder dslActionBuilder = new DslActionBuilder();
        return dslActionBuilder.dslAction;
    }

    public static DslAction startWorkflow(String name) {
        DslActionBuilder dslActionBuilder = new DslActionBuilder();
        return dslActionBuilder.dslAction;
    }

    public static DslAction stopWorkflow(String name) {
        DslActionBuilder dslActionBuilder = new DslActionBuilder();
        return dslActionBuilder.dslAction;
    }

    public static DslAction cancelWorkflow(String name) {
        DslActionBuilder dslActionBuilder = new DslActionBuilder();
        return dslActionBuilder.dslAction;
    }

}
