package erp.snippets.order.prediction;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NextOrderPredictorTest {

    private final NextOrderPredictor nextOrderPredictor = new NextOrderPredictor();

    private static Stream<Arguments> getExceptionTests() {
        return Stream
            .<Arguments>builder()
            .add(Arguments.of(null, IllegalArgumentException.class))
            .add(Arguments.of(Collections.emptyList(), UnsupportedOperationException.class))
            .build();
    }

    private static Stream<Arguments> getResultTests() {
        return Stream
            .<Arguments>builder()
            .add(Arguments.of(ListFactory.getListSet1(), LocalDate.of(2024, Month.JANUARY, 30)))
            .add(Arguments.of(ListFactory.getListSet2(), LocalDate.of(2024, Month.JANUARY, 18)))
            .add(Arguments.of(ListFactory.getListSet3(), LocalDate.of(2024, Month.JANUARY, 26)))
            .build();
    }


    @ParameterizedTest
    @MethodSource("getExceptionTests")
    void predictNextOrder(List<Order> input, Class<? extends Throwable> expected) {
        assertThatThrownBy(() -> nextOrderPredictor.predictNextOrder(input))
            .isInstanceOf(expected);
    }

    @ParameterizedTest
    @MethodSource("getResultTests")
    void predictNextOrder(List<Order> input, LocalDate expected) {
        assertThat(nextOrderPredictor.predictNextOrder(input))
            .isEqualTo(expected);
    }


    private static class ListFactory {

        private static ArrayList<Order> getListSet1() {
            return new ArrayList<>(List.of(
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 1, 8, 0)).build(),
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 10, 8, 0)).build(),
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 20, 8, 0)).build()
            ));
        }

        private static ArrayList<Order> getListSet2() {
            return new ArrayList<>(List.of(
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 5, 8, 0)).build(),
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 1, 8, 0)).build(),
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 12, 8, 0)).build()
            ));
        }

        private static ArrayList<Order> getListSet3() {
            return new ArrayList<>(List.of(
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 1, 8, 0)).build(),
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 4, 8, 0)).build(),
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 9, 8, 0)).build(),
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 13, 8, 0)).build(),
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 15, 8, 0)).build(),
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 19, 8, 0)).build(),
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 22, 8, 0)).build()
            ));
        }
    }
}
