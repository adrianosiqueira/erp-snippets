package erp.snippets.order.prediction;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
public class NextOrderPredictor {

    private static final ChronoUnit TIME_UNIT = ChronoUnit.DAYS;

    private boolean hasInsufficientItems(List<?> list) {
        return list.size() < 2;
    }

    public LocalDate predictNextOrder(List<Order> orders) {
        if (hasInsufficientItems(orders)) {
            log.error("At least 2 orders are needed to predict the next order");
            return LocalDate.now();
        }

        /*
         * Only the last three orders are used in the
         * calculation, and they must be ascending sorted
         * to prevent issues with negative average values.
         */
        OrderTimeSortAscending ascending = new OrderTimeSortAscending();

        orders = orders
            .stream()
            .sorted(ascending.reversed())
            .limit(3)
            .sorted(ascending)
            .toList();

        double averageDifference = (double) TIME_UNIT.between(
            orders.getFirst().getTimestamp(),
            orders.getLast().getTimestamp()
        ) / (orders.size() - 1);

        return orders.getLast().getTimestamp().plus(
            Math.round(averageDifference),
            TIME_UNIT
        ).toLocalDate();
    }
}
