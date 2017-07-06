package br.uece.beethoven.logic.dsl;

public class CommandBuilder {

    private CommandBuilder() { }

    public static Command startTask(String name) {
        Command command = new Command();
        command.setOperation(Command.CommandOperation.START_TASK);
        command.setTaskName(name);
        return command;
    }

    public static Command startTask(String name, String input) {
        Command command = new Command();
        command.setOperation(Command.CommandOperation.START_TASK);
        command.setTaskName(name);
        command.setInput(input);
        return command;
    }

//    public static Command startWorkflow(String name) {
//        Command command = new Command();
//        command.setOperation(Command.CommandOperation.START_WORKFLOW);
//        command.setWorkflowName(name);
//        return command;
//    }
//
//
//    public static DslAction startTask(String name) {
//        DslActionBuilder dslActionBuilder = new DslActionBuilder();
//        return dslActionBuilder.dslAction;
//    }
//
//    public static DslAction startTask(String name, String body) {
//        DslActionBuilder dslActionBuilder = new DslActionBuilder();
//        return dslActionBuilder.dslAction;
//    }
//
//    public static DslAction startWorkflow(String name) {
//        DslActionBuilder dslActionBuilder = new DslActionBuilder();
//        return dslActionBuilder.dslAction;
//    }
//
//    public static DslAction stopWorkflow(String name) {
//        DslActionBuilder dslActionBuilder = new DslActionBuilder();
//        return dslActionBuilder.dslAction;
//    }
//
//    public static DslAction cancelWorkflow(String name) {
//        DslActionBuilder dslActionBuilder = new DslActionBuilder();
//        return dslActionBuilder.dslAction;
//    }

}
