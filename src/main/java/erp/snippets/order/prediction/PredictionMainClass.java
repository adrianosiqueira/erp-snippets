package erp.snippets.order.prediction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class PredictionMainClass {

    public static void main(String[] args) {
        ArrayList<Order> orders = new ArrayList<>(List.of(
            Order.builder().timestamp(LocalDateTime.of(2023, Month.JANUARY, 5, 8, 0)).build(),
            Order.builder().timestamp(LocalDateTime.of(2023, Month.JANUARY, 1, 8, 0)).build(),
            Order.builder().timestamp(LocalDateTime.of(2023, Month.JANUARY, 12, 8, 0)).build()
        ));

        NextOrderPredictor nextOrderPredictor = new NextOrderPredictor();
        LocalDate          nextOrder          = nextOrderPredictor.predictNextOrder(orders);

        orders.forEach(System.out::println);
        System.out.println("nextOrder = " + nextOrder);
    }
}
