package store.formatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import store.dto.OrderRegisterDto;
import store.io.view.formatter.InputFormatter;

public class InputFormatterTest {
    @Test
    void 사용자의_주문_입력값이_저장에_알맞게_포매팅된다() {
        // given
        String input = "[사이다-2],[감자칩-1]";

        // when
        List<OrderRegisterDto> orderRegisterDtos = InputFormatter.fromInputToOrdersBeSaved(input);

        // then
        assertAll(() -> assertThat(orderRegisterDtos.size()).isEqualTo(2),
                () -> assertThat(orderRegisterDtos.getFirst().nameOfProduct()).isEqualTo("사이다"),
                () -> assertThat(orderRegisterDtos.getFirst().quantity()).isEqualTo(2L),
                () -> assertThat(orderRegisterDtos.getLast().nameOfProduct()).isEqualTo("감자칩"),
                () -> assertThat(orderRegisterDtos.getLast().quantity()).isEqualTo(1L));
    }
}
