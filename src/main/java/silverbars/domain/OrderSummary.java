package silverbars.domain;

import java.math.BigDecimal;

/**
 * Created by orenberenson on 10/02/2017.
 */
public class OrderSummary {
    public final BigDecimal quantity;
    public final BigDecimal price;

    public OrderSummary(BigDecimal price, BigDecimal quantity) {
        this.quantity = quantity;
        this.price = price;
    }
}
