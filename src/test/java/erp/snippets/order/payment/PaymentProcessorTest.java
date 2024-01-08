package erp.snippets.order.payment;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentProcessorTest {

    private final PaymentProcessor paymentProcessor = new PaymentProcessor();

    private static Stream<Arguments> getResultTests() {
        return Stream
            .<Arguments>builder()
            .add(Arguments.of(ListFactory.getInputSet1(), 200.0, ListFactory.getOutputSet1()))
            .add(Arguments.of(ListFactory.getInputSet2(), 185.5, ListFactory.getOutputSet2()))
            .build();
    }

    @ParameterizedTest
    @MethodSource("getResultTests")
    void processPayment(List<Order> orders, double paymentValue, List<Order> expected) {
        paymentProcessor.processPayment(orders, paymentValue);

        assertThat(orders)
            .containsExactlyElementsOf(expected);
    }


    private static class ListFactory {

        public static ArrayList<Order> getInputSet1() {
            return new ArrayList<>(List.of(
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 1, 10, 0)).totalValue(100.0).build(),
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JUNE, 1, 8, 0)).totalValue(100.0).build()
            ));
        }

        public static ArrayList<Order> getInputSet2() {
            return new ArrayList<>(List.of(
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JUNE, 1, 8, 0)).totalValue(100.0).build(),
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 1, 10, 0)).totalValue(125.0).build(),
                Order.builder().timestamp(LocalDateTime.of(2024, Month.MARCH, 1, 8, 0)).totalValue(25.0).build(),
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 1, 8, 0)).totalValue(50.0).build()
            ));
        }

        public static ArrayList<Order> getOutputSet1() {
            return new ArrayList<>(List.of(
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 1, 10, 0)).totalValue(100.0).paidValue(100.0).build(),
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JUNE, 1, 8, 0)).totalValue(100.0).paidValue(100.0).build()
            ));
        }

        public static ArrayList<Order> getOutputSet2() {
            return new ArrayList<>(List.of(
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 1, 8, 0)).totalValue(50.0).paidValue(50.0).build(),
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JANUARY, 1, 10, 0)).totalValue(125.0).paidValue(125.0).build(),
                Order.builder().timestamp(LocalDateTime.of(2024, Month.MARCH, 1, 8, 0)).totalValue(25.0).paidValue(10.5).build(),
                Order.builder().timestamp(LocalDateTime.of(2024, Month.JUNE, 1, 8, 0)).totalValue(100.0).build()
            ));
        }
    }
}
