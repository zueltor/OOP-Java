package ru.nsu.g.mustafin.calculator.functions;

import ru.nsu.g.mustafin.calculator.operations.Operation;
import ru.nsu.g.mustafin.calculator.operations.Push;
import ru.nsu.g.mustafin.calculator.operations.exceptions.OperationNotApplicableException;

import java.util.*;

public abstract class Function implements Operation {

    public abstract void apply(Stack<Integer> stack) throws OperationNotApplicableException;

    protected List<Operation> body;
    private Map<String, Operation> operations;
    private Map<String, Function> functions;

    protected void initialize(ArrayList<String> input, Map<String, Operation> operations, Map<String, Function> functions) {
        this.operations = operations;
        this.functions = functions;
        body = new ArrayList<>();
        parseOperations(input);
    }

    private void parseOperations(ArrayList<String> input) {
        String command;
        Operation op;
        for (int i = 0; i < input.size(); ) {
            command = input.remove(0);
            if (isNumber(command)) {
                op = new Push(Integer.parseInt(command));
            } else if (isStartLoop(command)) {
                op = new Loop(input, operations, functions);
            } else if (isEndLoop(command)) {
                break;
            } else if (functions.containsKey(command)) {
                op = functions.get(command);
            } else {
                op = operations.get(command);

            }
            body.add(op);
        }
    }

    boolean isStartLoop(String command) {
        return command.startsWith("[");
    }

    boolean isEndLoop(String command) {
        return command.startsWith("]");
    }

    boolean isNumber(String command) {
        try {
            Integer.parseInt(command);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
