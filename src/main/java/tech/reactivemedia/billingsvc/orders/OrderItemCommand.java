package tech.reactivemedia.billingsvc.orders;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

@Serdeable
public class OrderItemCommand {
    @NonNull
    @NotBlank
    private final String productId;
    private BigDecimal unitPrice;
    private Integer quantity;

    public OrderItemCommand(@NonNull @NotBlank String productId) {
        this(productId, BigDecimal.ZERO, 0);
    }

    @Creator
    public OrderItemCommand(@NonNull @NotBlank String productId, BigDecimal unitPrice, Integer quantity) {
        this.productId = productId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrderItemCommand.class.getSimpleName() + "[", "]")
                .add("productId='" + productId + "'")
                .toString();
    }

    public String getProductId() {
        return productId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrderItemCommand that = (OrderItemCommand) o;
        return Objects.equals(getProductId(), that.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId());
    }
}
