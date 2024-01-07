package erp.snippets.order.payment;

import java.util.Comparator;

public class OrderTimeSortAscending implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {
        return Comparator
            .comparing(Order::getTimestamp)
            .compare(o1, o2);
    }
}
