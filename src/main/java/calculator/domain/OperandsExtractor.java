package calculator.domain;

import static calculator.domain.CustomDelimiterHandler.extractCustomDelimiter;
import static calculator.domain.CustomDelimiterHandler.removeCustomDelimiterPattern;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class OperandsExtractor {

    private static final String DEFAULT_DELIMITER_STRING = ",|:";

    private OperandsExtractor() {
    }

    public static List<Integer> extractOperands(String input) {
        // 피연산자 추출을 위한 regex
        Optional<String> customDelimiter = extractCustomDelimiter(input);
        String regexStr = getDelimiterRegex(customDelimiter);

        // "//구분자\n" 부분 제거하기
        input = removeCustomDelimiterPattern(input);

        // 피연산자 찾기
        String[] strings = input.split(regexStr);

        // 피연산자들 정수 배열로 파싱 후 리스트 반환
        List<Integer> operands = parseToIntegerList(strings);

        // 음수 예외 발생
        validateNegativeOperands(operands);

        return operands;
    }

    private static List<Integer> parseToIntegerList(String[] strings) {
        List<Integer> operands;
        try {
            operands = Arrays.stream(strings)
                    .map(Integer::parseInt)
                    .toList();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
        return operands;
    }

    private static void validateNegativeOperands(List<Integer> operands) {
        boolean isNegative = operands.stream()
                .anyMatch(operand -> operand < 0);
        if (isNegative) {
            throw new IllegalArgumentException();
        }
    }

    private static String getDelimiterRegex(Optional<String> customDelimiter) {
        StringBuilder regexStr = new StringBuilder(DEFAULT_DELIMITER_STRING);
        customDelimiter.ifPresent(delimiter -> regexStr.append("|").append(Pattern.quote(delimiter)));
        return regexStr.toString();
    }
}
