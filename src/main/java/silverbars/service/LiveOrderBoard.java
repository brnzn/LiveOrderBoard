package silverbars.service;

import silverbars.domain.Order;
import silverbars.domain.OrderSummary;
import silverbars.registry.OrderRegistry;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by orenberenson on 10/02/2017.
 */
public class LiveOrderBoard {
    private final OrderRegistry orderRegistry;

    public LiveOrderBoard(OrderRegistry orderRegistry) {
        this.orderRegistry = orderRegistry;
    }

    public void register(Order order) {
        orderRegistry.register(order);
    }

    public void cancel(Order order) {
        orderRegistry.cancel(order);
    }

    public List<OrderSummary> getOrderSummary() {
        Map<Order.Type, Map<BigDecimal, BigDecimal>> groups = groupSumsByType(orderRegistry.getOrders());

        List<OrderSummary> sells = sort(groups.getOrDefault(Order.Type.SELL, Collections.emptyMap()), Map.Entry.comparingByKey());
        List<OrderSummary> buys = sort(groups.getOrDefault(Order.Type.BUY, Collections.emptyMap()), Map.Entry.<BigDecimal, BigDecimal>comparingByKey().reversed());

        return Stream.concat(sells.stream(), buys.stream()).collect(toList());
    }

    private Map<Order.Type, Map<BigDecimal, BigDecimal>> groupSumsByType(List<Order> orders) {
        return orders.stream().collect(
                Collectors.groupingBy(
                        Order::getType,
                        Collectors.groupingBy(
                                Order::getPrice,
                                Collectors.reducing(
                                        BigDecimal.ZERO,
                                        Order::getQuantity,
                                        BigDecimal::add
                                )
                        ))
        );
    }

    private List<OrderSummary> sort(Map<BigDecimal, BigDecimal> values, Comparator<Map.Entry<BigDecimal, BigDecimal>> comparator) {
        return values.entrySet().stream()
                .sorted(comparator)
                .collect(Collectors.mapping(e -> new OrderSummary(e.getKey(), e.getValue()), toList()));
    }
}
