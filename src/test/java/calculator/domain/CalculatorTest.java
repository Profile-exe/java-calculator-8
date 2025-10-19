package calculator.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CalculatorTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("normalInput")
    @DisplayName("연산 성공")
    void 연산_성공(String title, String input, int expected) {
        //when
        int result = Calculator.calculate(input);

        //then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> normalInput() {
        return Stream.of(
                Arguments.of("구분자 : -", "//-\\n1", 1),
                Arguments.of("구분자 : -", "//-\\n1,2:3", 6),
                Arguments.of("구분자 : ;", "//;\\n4;2,3", 9),
                Arguments.of("구분자: *", "//*\\n2*3*4*6", 15),
                Arguments.of("구분자: **", "//**\\n2,3**4", 9),
                Arguments.of("커스텀 구분자 없음", "1,2:3", 6)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidInput")
    @DisplayName("연산 실패")
    void 연산_실패(String title, String input) {
        //when, then
        assertThatThrownBy(() -> Calculator.calculate(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> invalidInput() {
        return Stream.of(
                Arguments.of("구분자 : -, abb 문자열 추가", "//-\\n1abb"),
                Arguments.of("구분자 : -, ** 문자열 추가", "//-\\n1,2:3**"),
                Arguments.of("구분자 : ;, 음수 피연산자 추가", "//;\\n4;-2,3"),
                Arguments.of("구분자 : ;, n 문자 추가", "//;\\n4;2,3n"),
                Arguments.of("구분자: *, wow 문자열 추가", "//*\\n2*3*4*6*wow"),
                Arguments.of("구분자: **, test 문자열 추가", "//**\\n2,3**4**test"),
                Arguments.of("커스텀 구분자 없음, error 문자열 추가", "1,2:3error")
        );
    }
}
