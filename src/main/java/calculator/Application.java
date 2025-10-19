package calculator;

import calculator.domain.Calculator;
import calculator.io.OutputView;
import camp.nextstep.edu.missionutils.Console;

public class Application {
    public static void main(String[] args) {
        OutputView outputView = new OutputView();
        outputView.showInputGuide();

        String input = Console.readLine();

        int result = Calculator.calculate(input);

        outputView.showResult(result);

        Console.close();
    }
}
