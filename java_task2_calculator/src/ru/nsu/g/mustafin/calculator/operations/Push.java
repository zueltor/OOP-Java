package ru.nsu.g.mustafin.calculator.operations;

import java.util.Stack;

public class Push implements Operation {
    private int a;

    public Push(int n) {
        a = n;
    }

    @Override
    public void apply(Stack<Integer> stack) {
        stack.push(a);
    }
}
