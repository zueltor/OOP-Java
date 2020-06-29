package ru.nsu.g.mustafin.calculator.functions;

import ru.nsu.g.mustafin.calculator.operations.Operation;
import ru.nsu.g.mustafin.calculator.operations.exceptions.OperationNotApplicableException;

import java.util.*;

public class Loop extends Function {

    public Loop(ArrayList<String> input, Map<String, Operation> operations,Map<String, Function> functions) {
        initialize(input, operations,functions);
    }

    @Override
    public void apply(Stack<Integer> stack) throws OperationNotApplicableException {

        Integer condition;
        while (true) {
            if (stack.empty()) {
                throw new OperationNotApplicableException();
            }
            condition = stack.pop();
            if (condition == 0) {
                return;
            }
            for (Operation op : body) {
                try {
                    op.apply(stack);
                } catch (OperationNotApplicableException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
