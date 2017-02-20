package silverbars.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import silverbars.domain.Order.Type;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by orenberenson on 10/02/2017.
 */
public class OrderTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validOrder() {
        Order order = new Order.Builder()
                .withId("id")
                .withQuantity(new BigDecimal(1))
                .withPrice(new BigDecimal(2))
                .withType(Type.SELL)
                .build();

        assertThat(order.getUserId(), is("id"));
        assertThat(order.getQuantity(), is(new BigDecimal(1)));
        assertThat(order.getPrice(), is(new BigDecimal(2)));
        assertThat(order.getType(), is(Type.SELL));
    }

    @Test
    public void testIdValidation() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("UserId is required");

        new Order.Builder()
                .withQuantity(new BigDecimal(1))
                .withPrice(new BigDecimal(2))
                .withType(Type.SELL)
                .build();
    }

    @Test
    public void testQuantityValidation() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Quantity is required");
        new Order.Builder()
                .withId("id")
                .withPrice(new BigDecimal(2))
                .withType(Type.SELL)
                .build();
    }
    @Test
    public void testPriceValidation() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Price is required");
        new Order.Builder()
                .withQuantity(new BigDecimal(1))
                .withId("id")
                .withType(Type.SELL)
                .build();
    }

    @Test
    public void testTypeValidation() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Type is required");
        new Order.Builder()
                .withQuantity(new BigDecimal(1))
                .withPrice(new BigDecimal(2))
                .withId("id")
                .build();
    }
}