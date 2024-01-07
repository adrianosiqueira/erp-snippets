package erp.snippets.order.payment;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PaymentProcessor {

    private boolean cannotPayAnymore(double paymentValue) {
        return paymentValue == 0.0;
    }

    private boolean orderAlreadyPaid(Order order) {
        return order.isPaid();
    }

    public void processPayment(List<Order> orders, double paymentValue) {
        try {
            orders.sort(new OrderTimeSortAscending());
        } catch (UnsupportedOperationException e) {
            log.error("Cannot sort orders because list is immutable", e);
            return;
        }

        for (Order order : orders) {
            if (orderAlreadyPaid(order)) continue;
            if (cannotPayAnymore(paymentValue)) break;

            double paymentRemaining = order.getTotalValue() - order.getPaidValue();
            double valueToPay       = Math.min(paymentValue, paymentRemaining);

            order.setPaidValue(order.getPaidValue() + valueToPay);
            paymentValue -= valueToPay;
        }
    }
}
