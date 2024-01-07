package erp.snippets.order.prediction;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class NextOrderPredictorTest {

    private final NextOrderPredictor nextOrderPredictor = new NextOrderPredictor();

    private static Stream<Arguments> getResultTests() {
        ArrayList<Order> list1 = new ArrayList<>(List.of(
            Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 5, 8, 0)).build(),
            Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 1, 8, 0)).build(),
            Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 12, 8, 0)).build()
        ));

        ArrayList<Order> list2 = new ArrayList<>(List.of(
            Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 1, 8, 0)).build(),
            Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 10, 8, 0)).build(),
            Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 20, 8, 0)).build()
        ));

        return Stream
            .<Arguments>builder()
            .add(Arguments.of(list1, LocalDate.of(2024, Month.JANUARY, 18)))
            .add(Arguments.of(list2, LocalDate.of(2024, Month.JANUARY, 30)))
            .build();
    }

    @ParameterizedTest
    @MethodSource("getResultTests")
    void predictNextOrder(List<Order> input, LocalDate expected) {
        assertThat(nextOrderPredictor.predictNextOrder(input))
            .isEqualTo(expected);
    }
}
