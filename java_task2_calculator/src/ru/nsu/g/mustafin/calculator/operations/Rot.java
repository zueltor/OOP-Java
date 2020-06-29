package ru.nsu.g.mustafin.calculator.operations;

import ru.nsu.g.mustafin.calculator.operations.exceptions.OperationNotApplicableException;

import java.util.Stack;

public class Rot implements Operation {
    @Override
    public void apply(Stack<Integer> stack) throws OperationNotApplicableException {
        if (stack.size() < 3) {
            throw new OperationNotApplicableException();
        }
        Integer a = stack.pop();
        Integer b = stack.pop();
        Integer c = stack.pop();

        stack.push(b);
        stack.push(a);
        stack.push(c);
    }
}
