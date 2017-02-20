package silverbars.registry;

import silverbars.domain.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by orenberenson on 10/02/2017.
 *
 * Don't really need it for this exercise, but created it just as an example...
 */
public class InMemoryOrderRegistry implements OrderRegistry {
    private List<Order> orders = new ArrayList<>();

    @Override
    public void register(Order order) {
        orders.add(order);
    }

    @Override
    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    @Override
    public void cancel(Order order) {
        orders = orders.stream().filter(o -> !isSameOrder(o, order)).collect(Collectors.toList());
    }

    private static boolean isSameOrder(Order left, Order right) {
        return left.getType() == right.getType()
                && left.getQuantity().equals(right.getQuantity())
                && left.getPrice().equals(right.getPrice())
                && left.getUserId().equals(right.getUserId());
    }
}
