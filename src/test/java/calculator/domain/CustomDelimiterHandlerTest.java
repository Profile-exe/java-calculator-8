package calculator.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CustomDelimiterHandlerTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("customDelimiterInputs")
    @DisplayName("커스텀 구분자 추출")
    void 커스텀_구분자_추출(String title, String input, String expectedDelimiter, boolean isExists) {
        //when
        Optional<String> customDelimiter = CustomDelimiterHandler.extractCustomDelimiter(input);

        //then
        assertThat(customDelimiter.isPresent()).isEqualTo(isExists);
        customDelimiter.ifPresent(delimiter -> assertThat(delimiter).isEqualTo(expectedDelimiter));
    }

    private static Stream<Arguments> customDelimiterInputs() {
        return Stream.of(
                Arguments.of("구분자 : -", "//-\\n1,2:3", "-", true),
                Arguments.of("구분자 : ;", "//;\\n", ";", true),
                Arguments.of("구분자: **", "//**\\n2,3**4", "**", true),
                Arguments.of("커스텀 구분자 없음", "1,2:3", "", false)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("customDelimiterPrefixes")
    @DisplayName("커스텀 구분자 접두사 제거")
    void 커스텀_구분자_접두사_제거(String title, String input, String expected) {
        //when
        String result = CustomDelimiterHandler.removeCustomDelimiterPattern(input);

        //then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> customDelimiterPrefixes() {
        return Stream.of(
                Arguments.of("구분자 : -", "//-\\n1,2:3", "1,2:3"),
                Arguments.of("구분자 : ;", "//;\\n", ""),
                Arguments.of("구분자: **", "//**\\n2,3**4", "2,3**4"),
                Arguments.of("커스텀 구분자 없음", "1,2:3", "1,2:3")
        );
    }
}
