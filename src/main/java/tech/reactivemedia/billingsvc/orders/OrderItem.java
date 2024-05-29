package tech.reactivemedia.billingsvc.orders;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Serdeable
public class OrderItem {
    @NonNull
    @NotBlank
    private String productId;
    private BigDecimal unitPrice;
    private Integer quantity;

    public OrderItem(@NonNull @NotBlank String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "OrderItem [" + "productId=" + productId + "]";
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((productId == null) ? 0 : productId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderItem other = (OrderItem) obj;
        if (productId == null) {
            return other.productId == null;
        } else
            return productId.equals(other.productId);
    }

}
