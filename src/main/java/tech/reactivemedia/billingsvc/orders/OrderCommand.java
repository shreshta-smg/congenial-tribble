package tech.reactivemedia.billingsvc.orders;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import java.math.BigDecimal;
import java.security.SignatureException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Serdeable
public class OrderCommand {
    private final String orderId;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private List<OrderItem> orderItems;
    private BigDecimal totalAmount;
    private BigDecimal amountPaid;
    private BigDecimal balance;

    public OrderCommand(String orderId, LocalDate invoiceDate, LocalDate dueDate) throws SignatureException {
        this(orderId, invoiceDate, dueDate, new ArrayList<>(), null, null, null);
    }

    public OrderCommand(LocalDate invoiceDate, LocalDate dueDate) throws SignatureException {
        this(OrderId.createOrderId().getOrderId(), invoiceDate, dueDate);
    }

    @Creator
    public OrderCommand(String orderId, @Nullable LocalDate invoiceDate, @Nullable LocalDate dueDate,
            List<OrderItem> orderItems, BigDecimal totalAmount, BigDecimal amountPaid, BigDecimal balance) throws SignatureException {
        this.orderId = Objects.isNull(orderId) ? OrderId.createOrderId().getOrderId() : orderId;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.orderItems = orderItems;
        this.totalAmount = totalAmount;
        this.amountPaid = amountPaid;
        this.balance = balance;
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
        this.orderItems = orderItems;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
