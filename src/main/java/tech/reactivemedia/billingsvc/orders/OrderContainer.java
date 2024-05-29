package tech.reactivemedia.billingsvc.orders;

import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderContainer {
    @NotNull
    private final Map<String, Order> orders = new ConcurrentHashMap<>();

    public Map<String, Order> getOrders() {
        return orders;
    }
}
