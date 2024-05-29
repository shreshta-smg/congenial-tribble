package tech.reactivemedia.billingsvc.payments;

import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentContainer {
    @NotNull
    private final Map<String, Payment> payments = new ConcurrentHashMap<>();

    public Map<String, Payment> getPayments() {
        return payments;
    }
}
