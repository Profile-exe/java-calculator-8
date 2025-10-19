package calculator.domain;

import static calculator.domain.OperandsExtractor.extractOperands;

import java.util.List;

public class Calculator {

    private Calculator() {
    }

    public static int calculate(String input) {
        List<Integer> operands = extractOperands(input);
        return operands.stream().mapToInt(Integer::intValue).sum();
    }
}
