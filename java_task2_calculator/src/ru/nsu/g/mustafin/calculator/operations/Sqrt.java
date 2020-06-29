package ru.nsu.g.mustafin.calculator.operations;

import ru.nsu.g.mustafin.calculator.operations.exceptions.OperationNotApplicableException;

import java.util.Stack;

public class Sqrt implements Operation {
    @Override
    public void apply(Stack<Integer> stack) throws OperationNotApplicableException {
        if (stack.empty()) {
            throw new OperationNotApplicableException();
        }
        Integer a = stack.pop();
        stack.push((int) Math.sqrt(a));
    }
}
