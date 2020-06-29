package ru.nsu.g.mustafin.calculator.functions;

import ru.nsu.g.mustafin.calculator.operations.Operation;
import ru.nsu.g.mustafin.calculator.operations.exceptions.OperationNotApplicableException;

import java.util.*;


public class Define extends Function {

    public Define(ArrayList<String> input, Map<String, Operation> operations,Map<String, Function> functions) {
        initialize(input, operations,functions);
    }

    @Override
    public void apply(Stack<Integer> stack) {
        for (Operation op : body) {
            try {
                op.apply(stack);
            } catch (OperationNotApplicableException e) {
                e.printStackTrace();
            }
        }
    }
}

