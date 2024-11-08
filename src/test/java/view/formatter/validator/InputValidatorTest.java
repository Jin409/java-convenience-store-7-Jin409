package view.formatter.validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.io.view.Constants;
import store.io.view.validator.InputValidator;

public class InputValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"사이다-2]", "[사이다-2", "[사이다2]", "[##-2]", "", "   ", "[사이다-ㅇ]", "[사이다-##]"})
    void 유효하지_않은_주문_입력값에_대해_예외를_던진다(String input) {
        // when & then
        assertThatThrownBy(() -> InputValidator.validateOrdersFromCustomer(List.of(input))).isInstanceOf(
                IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"[사이다-2],[계란-2]"})
    void 유효한_주문_입력값에_대해_예외를_던지지_않는다(String input) {
        // when & then
        assertDoesNotThrow(() -> InputValidator.validateOrdersFromCustomer(List.of(input.split(Constants.DELIMITER))));
    }
}

