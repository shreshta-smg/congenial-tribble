package tech.reactivemedia.billingsvc.orders;

import io.micronaut.serde.annotation.Serdeable;
import tech.reactivemedia.billingsvc.utils.HashUtil;

import java.security.SignatureException;
import java.util.Objects;
import java.util.StringJoiner;

@Serdeable
public class OrderId {
    private final String orderId;

    private OrderId(String orderId) throws SignatureException {
        if(!HashUtil.isValidSHA1(orderId)) {
            throw new SignatureException(String.format("Invalid hash for id: %s", orderId));
        }
        this.orderId = orderId;
    }

    public static OrderId createOrderId() throws SignatureException {
        return new OrderId(HashUtil.generateOrderId());
    }

    public static OrderId getOrderId(String orderId) throws SignatureException { return new OrderId(orderId); }
    public String getOrderId() {
        return orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrderId orderId1 = (OrderId) o;
        return Objects.equals(getOrderId(), orderId1.getOrderId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOrderId());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrderId.class.getSimpleName() + "[", "]")
                .add("orderId='" + orderId + "'")
                .toString();
    }
}
