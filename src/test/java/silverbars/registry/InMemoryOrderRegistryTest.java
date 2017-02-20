package silverbars.registry;

import org.junit.Before;
import org.junit.Test;
import silverbars.domain.Order;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


/**
 * Created by orenberenson on 10/02/2017.
 */
public class InMemoryOrderRegistryTest {
    private InMemoryOrderRegistry registry;

    @Before
    public void init() {
        registry = new InMemoryOrderRegistry();
    }

    @Test
    public void registerOrder() {
        Order order = createOrder(Order.Type.SELL, BigDecimal.ONE, BigDecimal.ONE);
        registry.register(order);

        assertThat(registry.getOrders().size(), is(1));
    }

    @Test
    public void cancelOrderShouldRemoveAllOrdersWithSameValues() {
        Order order = createOrder(Order.Type.SELL, BigDecimal.ONE, BigDecimal.ONE);
        Order order1 = createOrder(Order.Type.SELL, BigDecimal.ONE, BigDecimal.ONE);

        registry.register(order);
        registry.register(order1);

        assertThat(registry.getOrders().size(), is(2));

        registry.cancel(order);

        assertThat(registry.getOrders().size(), is(0));
    }

    private Order createOrder(Order.Type type, BigDecimal price, BigDecimal quantity) {
        return new Order.Builder()
                .withQuantity(quantity)
                .withId("id")
                .withPrice(price)
                .withType(type)
                .build();
    }

}