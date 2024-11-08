package store.formatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import store.dto.ProductDisplayDto;
import store.io.view.formatter.OutputFormatter;

public class OutputFormatterTest {
    @Test
    void 알맞은_형태로_상품의_정보들을_포매팅한다() {
        // given
        List<ProductDisplayDto> productDisplayDtos = List.of(new ProductDisplayDto("상품명", 1500L, 3L, "MD추천"));

        // when
        List<String> information = OutputFormatter.formatInformationOfProducts(productDisplayDtos);

        // then
        assertAll(() -> assertThat(information.size()).isEqualTo(1),
                () -> assertThat(information.getFirst()).isEqualTo("- 상품명 1500원 3개 MD추천")
        );
    }
}
