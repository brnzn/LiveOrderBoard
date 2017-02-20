package silverbars.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import silverbars.domain.Order;
import silverbars.domain.OrderSummary;
import silverbars.registry.OrderRegistry;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * Created by orenberenson on 10/02/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class LiveOrderBoardTest {
    @Mock
    private OrderRegistry orderRegistry;

    @InjectMocks
    private LiveOrderBoard liveOrderBoard;

    @Test
    public void addOrder() {
        Order order = createOrder();

        liveOrderBoard.register(order);

        verify(orderRegistry).register(order);
    }

    @Test
    public void cancelOrder() {
        Order order = createOrder();

        liveOrderBoard.cancel(order);

        verify(orderRegistry).cancel(order);
    }

    @Test
    public void orderSummaryGroupedByPriceInAscendingOrderForSell() {
        List<Order> orders = Arrays.asList(
                createOrder(Order.Type.SELL, BigDecimal.ONE, BigDecimal.ONE),
                createOrder(Order.Type.SELL, BigDecimal.ONE, BigDecimal.ONE),
                createOrder(Order.Type.SELL, new BigDecimal(2), BigDecimal.ONE)
        );

        given(orderRegistry.getOrders()).willReturn(orders);

        List<OrderSummary> summaries = liveOrderBoard.getOrderSummary();

        assertThat(summaries.size(), is(2));
        assertReflectionEquals(summaries.get(0), new OrderSummary(BigDecimal.ONE, new BigDecimal(2)));
        assertReflectionEquals(summaries.get(1), new OrderSummary(new BigDecimal(2), BigDecimal.ONE));
    }

    @Test
    public void orderSummaryGroupedByPriceInDescendingOrderForBuy() {
        List<Order> orders = Arrays.asList(
                createOrder(Order.Type.BUY, BigDecimal.ONE, BigDecimal.ONE),
                createOrder(Order.Type.BUY, BigDecimal.ONE, BigDecimal.ONE),
                createOrder(Order.Type.BUY, new BigDecimal(2), BigDecimal.ONE)
        );

        given(orderRegistry.getOrders()).willReturn(orders);

        List<OrderSummary> summaries = liveOrderBoard.getOrderSummary();

        assertThat(summaries.size(), is(2));
        assertReflectionEquals(summaries.get(0), new OrderSummary(new BigDecimal(2), BigDecimal.ONE));
        assertReflectionEquals(summaries.get(1), new OrderSummary(BigDecimal.ONE, new BigDecimal(2)));
    }

    @Test
    public void mixOrderSummariesGroupedByPrice() {
        List<Order> orders = Arrays.asList(
                createOrder(Order.Type.SELL, BigDecimal.ONE, BigDecimal.ONE),
                createOrder(Order.Type.BUY, new BigDecimal(4), BigDecimal.ONE),
                createOrder(Order.Type.SELL, BigDecimal.ONE, BigDecimal.ONE),
                createOrder(Order.Type.BUY, new BigDecimal(4), BigDecimal.ONE),
                createOrder(Order.Type.SELL, new BigDecimal(2), BigDecimal.ONE),
                createOrder(Order.Type.BUY, new BigDecimal(5), BigDecimal.ONE)
        );

        given(orderRegistry.getOrders()).willReturn(orders);

        List<OrderSummary> summaries = liveOrderBoard.getOrderSummary();

        assertThat(summaries.size(), is(4));

        assertReflectionEquals(summaries.get(0), new OrderSummary(BigDecimal.ONE, new BigDecimal(2)));
        assertReflectionEquals(summaries.get(1), new OrderSummary(new BigDecimal(2), BigDecimal.ONE));
        assertReflectionEquals(summaries.get(2), new OrderSummary(new BigDecimal(5), BigDecimal.ONE));
        assertReflectionEquals(summaries.get(3), new OrderSummary(new BigDecimal(4), new BigDecimal(2)));
    }


    private Order createOrder() {
        return createOrder(Order.Type.SELL, BigDecimal.ONE, BigDecimal.ONE);
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