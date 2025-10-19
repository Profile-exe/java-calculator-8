package calculator.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThatList;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OperandsExtractorTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("normalInput")
    @DisplayName("피연산자 추출")
    void 피연산자_추출(String title, String input, List<Integer> expectedOperands) {
        //when
        List<Integer> operands = OperandsExtractor.extractOperands(input);

        //then
        assertThatList(operands).containsExactlyElementsOf(expectedOperands);
    }

    private static Stream<Arguments> normalInput() {
        return Stream.of(
                Arguments.of("구분자 : -", "//-\\n1", List.of(1)),
                Arguments.of("구분자 : -", "//-\\n1,2:3", List.of(1, 2, 3)),
                Arguments.of("구분자 : ;", "//;\\n4;2,3", List.of(4, 2, 3)),
                Arguments.of("구분자: *", "//*\\n2*3*4*6", List.of(2, 3, 4, 6)),
                Arguments.of("구분자: **", "//**\\n2,3**4", List.of(2, 3, 4)),
                Arguments.of("커스텀 구분자 없음", "1,2:3", List.of(1, 2, 3))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidInput")
    @DisplayName("추출한 피연산자가 숫자가 아닌 경우 오류 발생")
    void 추출한_피연산자가_숫자가_아닌_경우_오류_발생(String title, String input) {
        //when, then
        assertThatThrownBy(() -> OperandsExtractor.extractOperands(input))
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
