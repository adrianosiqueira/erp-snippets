package erp.snippets.order.prediction;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class Order {
    private LocalDateTime timestamp;
}
