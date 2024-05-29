package tech.reactivemedia.billingsvc.orders;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Serdeable
public class Order {
    @NonNull
    @NotBlank
    private final String orderId;
    @Nullable
    private LocalDate invoiceDate;
    @Nullable
    private LocalDate dueDate;
    private List<OrderItem> orderItems;
    private BigDecimal totalAmount;
    private BigDecimal amountPaid;
    private BigDecimal balance;

    public Order(@NonNull @NotBlank String orderId, LocalDate invoiceDate, LocalDate dueDate) {
        this.orderId = orderId;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        totalAmount = BigDecimal.ZERO;
        amountPaid = BigDecimal.ZERO;
        balance = totalAmount;
        orderItems = new ArrayList<>();
    }

    public void calculateTotal() {
        if (!orderItems.isEmpty()) {
            var totalAmt = orderItems.parallelStream()
                    .map(y -> y.getUnitPrice().multiply(BigDecimal.valueOf(y.getQuantity().longValue())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            setTotalAmount(totalAmt);
        }
    }

    public void calculateBalance() {
        if (BigDecimal.ZERO.equals(totalAmount)) {
            throw new IllegalArgumentException("No Amount To Be Paid!");
        }
        setBalance(totalAmount.subtract(amountPaid));
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
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
        Order other = (Order) obj;
        if (orderId == null) {
            return other.orderId == null;
        } else return orderId.equals(other.orderId);
    }

    public String getOrderId() {
        return orderId;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        if(CollectionUtils.isNotEmpty(orderItems)) this.orderItems = orderItems;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        if(!totalAmount.equals(BigDecimal.ZERO)) this.totalAmount = totalAmount;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        if(!amountPaid.equals(BigDecimal.ZERO)) this.amountPaid = amountPaid;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
