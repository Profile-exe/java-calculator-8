package calculator.domain;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomDelimiterHandler {

    private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile("^//(.*?)\\\\n");

    private CustomDelimiterHandler() {
    }

    public static Optional<String> extractCustomDelimiter(String input) {
        Matcher matcher = CUSTOM_DELIMITER_PATTERN.matcher(input);

        if (matcher.find()) {
            return Optional.of(matcher.group(1)); // 캡처 그룹 "()" 내 구분자
        }

        return Optional.empty();
    }

    public static String removeCustomDelimiterPattern(String input) {
        return input.replaceAll(CUSTOM_DELIMITER_PATTERN.toString(), "");
    }
}
