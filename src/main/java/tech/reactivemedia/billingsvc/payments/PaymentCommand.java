package tech.reactivemedia.billingsvc.payments;

import io.micronaut.core.annotation.Creator;
import io.micronaut.serde.annotation.Serdeable;
import tech.reactivemedia.billingsvc.utils.HashUtil;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.math.BigDecimal;
import java.security.SignatureException;

@Serdeable
public class PaymentCommand {
    private final String paymentId;
    private final String orderId;
    private final String customerId;
    private LocalDate paymentDate;
    private BigDecimal amountPaid;
    private String paymentRef;
    private String staffId;
    private String remarks;

    public PaymentCommand(String orderId, String customerId, BigDecimal amountPaid,
            LocalDate paymentDate) throws SignatureException {
        this(PaymentId.createAPaymentId().getPaymentId(), orderId, customerId, paymentDate, amountPaid,
                null, null, null);
    }

    @Creator
    public PaymentCommand(String paymentId, String orderId, String customerId, LocalDate paymentDate,
            BigDecimal amountPaid, String paymentRef, String staffId, String remarks) throws SignatureException {
        this.paymentId = Objects.isNull(paymentId) ? PaymentId.createAPaymentId().getPaymentId()
                : Optional.of(paymentId).filter(HashUtil::isValidSHA1).orElseThrow();
        this.orderId = Optional.of(orderId).filter(HashUtil::isValidSHA1).orElseThrow();
        this.customerId = customerId;
        this.paymentDate = paymentDate;
        this.amountPaid = amountPaid;
        this.paymentRef = paymentRef;
        this.staffId = staffId;
        this.remarks = remarks;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPaymentRef() {
        return paymentRef;
    }

    public void setPaymentRef(String paymentRef) {
        this.paymentRef = paymentRef;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
