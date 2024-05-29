package tech.reactivemedia.billingsvc.payments;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.util.StringUtils;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;

@Serdeable
public class Payment {
    @NonNull
    @NotBlank
    private final String paymentId;
    @NonNull
    @NotBlank
    private final String orderId;
    @NonNull
    @NotBlank
    private final String customerId;
    @Nullable
    private LocalDate paymentDate;
    private BigDecimal amountPaid;
    @Nullable
    private String paymentRef;
    @Nullable
    private String staffId;
    @Nullable
    private String remarks;

    public Payment(@NonNull @NotBlank String paymentId,
                   String customerId, String orderId,
                   LocalDate paymentDate) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.paymentDate = paymentDate;
        amountPaid = BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return "Payment [paymentId=" + paymentId + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((paymentId == null) ? 0 : paymentId.hashCode());
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
        Payment other = (Payment) obj;
        if (paymentId == null) {
            if (other.paymentId != null)
                return false;
        } else if (!paymentId.equals(other.paymentId))
            return false;
        return true;
    }

    public String paymentId() {
        return paymentId;
    }

    public String orderId() {
        return orderId;
    }

    public String customerId() {
        return customerId;
    }

    public LocalDate paymentDate() {
        return paymentDate;
    }

    public Payment setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public BigDecimal amountPaid() {
        return amountPaid;
    }

    public Payment setAmountPaid(BigDecimal amountPaid) {
        if(!amountPaid.equals(BigDecimal.ZERO)) this.amountPaid = amountPaid;
        return this;
    }

    public String paymentRef() {
        return paymentRef;
    }

    public Payment setPaymentRef(String paymentRef) {
        if(StringUtils.isNotEmpty(paymentRef)) this.paymentRef = paymentRef;
        return this;
    }

    public String staffId() {
        return staffId;
    }

    public Payment setStaffId(String staffId) {
        if(StringUtils.isNotEmpty(staffId)) this.staffId = staffId;
        return this;
    }

    public String remarks() {
        return remarks;
    }

    public Payment setRemarks(String remarks) {
        if(StringUtils.isNotEmpty(remarks)) this.remarks = remarks;
        return this;
    }
}
