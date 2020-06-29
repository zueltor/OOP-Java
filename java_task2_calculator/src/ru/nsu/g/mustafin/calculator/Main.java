package ru.nsu.g.mustafin.calculator;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		try(Calculator calculator = new Calculator(args)) {
			calculator.run();
		}

	}
}
