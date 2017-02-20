package silverbars.registry;

import silverbars.domain.Order;

import java.util.List;

/**
 * Created by orenberenson on 10/02/2017.
 */
public interface OrderRegistry {
    void register(Order order);
    List<Order> getOrders();
    void cancel(Order order);
}
