package silverbars.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by orenberenson on 10/02/2017.
 */
public class Order {
    public enum Type {
        BUY,
        SELL
    }

    private final String userId;
    private final BigDecimal quantity;
    private final BigDecimal price;
    private final Type type;

    private Order(Builder builder) {
        this.userId = builder.userId;
        this.price = builder.price;
        this.quantity = builder.quantity;
        this.type = builder.type;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Type getType() {
        return type;
    }


    public static class Builder {
        private String userId;
        private BigDecimal quantity;
        private BigDecimal price;
        private Type type;

        public Order build() {
            validate();

            return new Order(this);
        }

        private void validate() {
            Objects.requireNonNull(userId, "UserId is required");
            Objects.requireNonNull(price, "Price is required");
            Objects.requireNonNull(quantity, "Quantity is required");
            Objects.requireNonNull(type, "Type is required");
        }

        public Builder withId(String id) {
            this.userId = id;
            return this;
        }

        public Builder withQuantity(BigDecimal quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder withType(Type type) {
            this.type = type;
            return this;
        }
    }
}
