package erp.snippets.order.payment;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class PaymentMainClass {

    public static void main(String[] args) {
        ArrayList<Order> orders = new ArrayList<>(List.of(
            // $ 300 remaining
            Order.builder().timestamp(LocalDateTime.of(2023, Month.JUNE, 1, 8, 0)).totalValue(100.0).build(),
            Order.builder().timestamp(LocalDateTime.of(2023, Month.JANUARY, 1, 10, 0)).totalValue(125.0).build(),
            Order.builder().timestamp(LocalDateTime.of(2023, Month.MARCH, 1, 8, 0)).totalValue(25.0).build(),
            Order.builder().timestamp(LocalDateTime.of(2023, Month.JANUARY, 1, 8, 0)).totalValue(50.0).build()
        ));

        PaymentProcessor paymentProcessor = new PaymentProcessor();
        paymentProcessor.processPayment(orders, 60.0);
        paymentProcessor.processPayment(orders, 125.5);

        orders.forEach(System.out::println);
    }
}
