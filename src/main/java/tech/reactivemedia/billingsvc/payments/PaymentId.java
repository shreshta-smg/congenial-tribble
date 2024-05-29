package tech.reactivemedia.billingsvc.payments;

import io.micronaut.serde.annotation.Serdeable;
import tech.reactivemedia.billingsvc.utils.HashUtil;

import java.security.SignatureException;
import java.util.Objects;
import java.util.StringJoiner;

@Serdeable
public class PaymentId {
    private final String paymentId;

    private PaymentId(String paymentId) throws SignatureException {
        if(!HashUtil.isValidSHA1(paymentId)) {
            throw new SignatureException(String.format("Invalid hash for id: %s", paymentId));
        }
        this.paymentId = paymentId;
    }

    public static PaymentId createAPaymentId() throws SignatureException {
        return new PaymentId(HashUtil.generatePaymentId());
    }

    public static PaymentId getPaymentId(String paymentId) throws SignatureException { return new PaymentId(paymentId); }
    public String getPaymentId() {
        return paymentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PaymentId paymentId1 = (PaymentId) o;
        return Objects.equals(paymentId, paymentId1.paymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPaymentId());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PaymentId.class.getSimpleName() + "[", "]")
                .add("paymentId='" + paymentId + "'")
                .toString();
    }
}
