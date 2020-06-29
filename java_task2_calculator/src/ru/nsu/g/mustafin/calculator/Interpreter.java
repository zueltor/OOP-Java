package ru.nsu.g.mustafin.calculator;

import ru.nsu.g.mustafin.calculator.functions.Define;
import ru.nsu.g.mustafin.calculator.functions.Function;
import ru.nsu.g.mustafin.calculator.functions.Loop;
import ru.nsu.g.mustafin.calculator.operations.Operation;
import ru.nsu.g.mustafin.calculator.operations.Push;
import ru.nsu.g.mustafin.calculator.operations.exceptions.OperationNotApplicableException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Interpreter {
    Stack<Integer> stack;
    Map<String, Operation> operations;
    Map<String, Function> functions;
    Properties prop;

    Interpreter() {
        stack = new Stack<>();
        operations = new HashMap<>();
        functions = new HashMap<>();
        loadOperations();
    }

    public void run(Scanner input) {
        String command = input.next();
        Operation op;
        if (isDefine(command)) {
            addFunction(input);
            return;
        } else if (isStartLoop(command)) {
            op = new Loop(getLoopBody(input), operations,functions);
        } else if (isNumber(command)) {
            op = new Push(Integer.parseInt(command));
        } else if (functions.containsKey(command)) {
            op = functions.get(command);
        } else{
            op = operations.get(command);
        }
        try {
            op.apply(stack);
        } catch (OperationNotApplicableException | NullPointerException ex) {
            ex.getMessage();
        }
    }

    boolean isNumber(String command) {
        try {
            Integer.parseInt(command);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    boolean isStartLoop(String command) {
        return command.startsWith("[");
    }

    boolean isDefine(String command) {
        return command.equals("define");

    }

    void addFunction(Scanner input) {
        String name = input.next();
        Function f = new Define(getBody(input, ";"), operations,functions);
        functions.put(name, f);
    }


    ArrayList<String> getBody(Scanner input, String delimiter) {
        String command;
        ArrayList<String> body = new ArrayList<>();
        while (input.hasNext()) {
            command = input.next();
            if (command.equals(delimiter)) {
                return body;
            }
            if (command.endsWith(delimiter)) {
                body.add(command.substring(0, command.length() - 1));
                return body;
            }
            body.add(command);
        }
        return body;
    }

    ArrayList<String> getLoopBody(Scanner input){
        String command;
        int nested_loops=0;
        ArrayList<String> body = new ArrayList<>();
        while (input.hasNext()) {
            command = input.next();
            if (command.equals("[")) {
                nested_loops++;
            }
            if (command.equals("]")) {
                if (nested_loops==0){
                    return body;
                }
                nested_loops--;
            }

            body.add(command);
        }
        return body;
    }

    public void loadOperations() {
        try (InputStream in = this.getClass().getResourceAsStream("operations.properties")) {
            prop = new Properties();
            prop.load(in);
            for (var prop : prop.entrySet()) {
                Operation op = (Operation) Class
                        .forName((String) prop.getValue())
                        .getDeclaredConstructor()
                        .newInstance();
                operations.put((String) prop.getKey(), op);
            }
        } catch (IOException | ClassNotFoundException | NoSuchMethodException ex) {
            System.out.println(ex.getMessage());
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
