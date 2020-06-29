package ru.nsu.g.mustafin.calculator.operations;

import ru.nsu.g.mustafin.calculator.operations.exceptions.OperationNotApplicableException;

import java.util.Stack;

public interface Operation {
    void apply(Stack<Integer> stack) throws OperationNotApplicableException;
}
