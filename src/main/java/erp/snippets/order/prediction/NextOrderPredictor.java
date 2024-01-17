package erp.snippets.order.prediction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Slf4j
public class NextOrderPredictor {

    private static final ChronoUnit TIME_UNIT   = ChronoUnit.DAYS;
    private static final int        ORDER_COUNT = 3;

    private double calculateAverageInterval(List<Order> orders) {
        return (double) TIME_UNIT.between(
            orders.getFirst().getTimestamp(),
            orders.getLast().getTimestamp()
        ) / (orders.size() - 1);
    }

    private void evaluate(List<?> list) {
        Stream
            .of(Evaluation.values())
            .filter(evaluation -> evaluation.getCondition().test(list))
            .findFirst()
            .ifPresent(evaluation -> {
                throw evaluation.getException();
            });
    }

    private List<Order> getLatestOrders(List<Order> orders) {
        /*
         * Only the last three orders are used in the
         * calculation, and they must be ascending sorted
         * to prevent issues with negative average values.
         */
        OrderTimeSortAscending ascending = new OrderTimeSortAscending();

        return orders
            .stream()
            .sorted(ascending.reversed())
            .limit(ORDER_COUNT)
            .sorted(ascending)
            .toList();
    }

    public LocalDate predictNextOrder(List<Order> orders) {
        evaluate(orders);

        orders = getLatestOrders(orders);
        double averageInterval = calculateAverageInterval(orders);

        return orders.getLast().getTimestamp().plus(
            Math.round(averageInterval),
            TIME_UNIT
        ).toLocalDate();
    }


    @RequiredArgsConstructor
    @Getter
    private enum Evaluation {

        NULL(
            Objects::isNull,
            new IllegalArgumentException("Order list cannot be null")
        ),

        SIZE(
            list -> list.size() < 2,
            new UnsupportedOperationException("At least 2 orders are needed to predict the next order")
        );

        private final Predicate<List<?>> condition;
        private final RuntimeException   exception;
    }
}
