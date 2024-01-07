package erp.snippets.order.payment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class Order {

    private LocalDateTime timestamp;
    private double        totalValue;
    private double        paidValue;

    public final boolean isPaid(){
        return totalValue == paidValue;
    }
}
