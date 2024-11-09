package store.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnswerSignTest {
    @Test
    void 응답_사인을_찾는_경우_유효하지_않은_입력값에_대해_예외를_던진다() {
        // given
        String invalidSign = "invalid";

        // when & then
        Assertions.assertThatThrownBy(() -> AnswerSign.getBySign(invalidSign))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(AnswerSignErrorMessages.INVALID_ANSWER_SIGN);
    }
}
