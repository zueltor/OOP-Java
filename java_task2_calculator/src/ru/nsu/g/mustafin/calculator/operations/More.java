package ru.nsu.g.mustafin.calculator.operations;

import ru.nsu.g.mustafin.calculator.operations.exceptions.OperationNotApplicableException;

import java.util.Stack;

public class More implements Operation {
    @Override
    public void apply(Stack<Integer> stack) throws OperationNotApplicableException {
        if (stack.size() < 2) {
            throw new OperationNotApplicableException();
        }
        Integer a = stack.pop();
        Integer b = stack.pop();
        stack.push((b > a) ? 1 : 0);
    }
}
