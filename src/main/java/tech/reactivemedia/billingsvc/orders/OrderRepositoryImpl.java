package tech.reactivemedia.billingsvc.orders;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.eclipsestore.RootProvider;
import io.micronaut.eclipsestore.annotations.StoreParams;
import io.micronaut.eclipsestore.annotations.StoreReturn;
import jakarta.inject.Singleton;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Singleton
public class OrderRepositoryImpl implements OrderRepository {

    private static final Logger log = LoggerFactory.getLogger(OrderRepositoryImpl.class);
    private final Map<String, Order> orders;

    public OrderRepositoryImpl(RootProvider<OrderContainer> orderRootProvider) {
        orders = orderRootProvider.root().getOrders();
    }

    @Override
    public @NonNull Collection<Order> list() {
        return orders.values();
    }

    @Override
    public @NonNull Order create(@NonNull @Valid OrderCommand order) throws OrderDuplicateException {
        if (orders.containsKey(order.getOrderId())) {
            throw new OrderDuplicateException(order.getOrderId());
        }
        return performCreate(orders, order);
    }

    @StoreParams("orders")
    protected Order performCreate(Map<String, Order> orders, OrderCommand order) {
        Order newOrder = new Order(order.getOrderId(), order.getInvoiceDate(), order.getDueDate());
        newOrder.setOrderItems(order.getOrderItems());
        newOrder.calculateTotal();
        newOrder.setAmountPaid(order.getAmountPaid());
        newOrder.calculateBalance();
        orders.put(order.getOrderId(), newOrder);
        return newOrder;
    }

    @Override
    public @Nullable Order update(@NonNull @Valid OrderCommand order) {
        Order foundOrder = orders.get(order.getOrderId());
        if (foundOrder != null) {
            return performUpdate(foundOrder, order);
        }
        return null;
    }

    @StoreReturn
    protected Order performUpdate(@NonNull Order order, @NonNull OrderCommand orderCommand) {
        order.setInvoiceDate(orderCommand.getInvoiceDate());
        order.setDueDate(orderCommand.getDueDate());
        order.setOrderItems(orderCommand.getOrderItems());
        order.calculateTotal();
        order.setAmountPaid(orderCommand.getAmountPaid());
        order.calculateBalance();
        return order;
    }

    @Override
    public @Nullable Order find(@NonNull @NotBlank String orderId) {
        return orders.get(orderId);
    }

    @Override
    public void delete(@NonNull @Valid OrderCommand order) {
        performDelete(order);
    }

    @StoreReturn
    protected void performDelete(OrderCommand orderCommand) {
        String orderId = orderCommand.getOrderId();
        if (orders.remove(orderId) != null) {
            log.info("Deleted order with id: {}", orderId);
        }
        log.info("Unable to deleted order with id: {}", orderId);
    }

    @Override
    public Order addOrderItems(@NonNull @NotBlank String orderId,
            @NonNull @Valid List<OrderItemCommand> orderItemCommands) {
        Order order = orders.get(orderId);
        if (orderItemCommands.isEmpty()) {
            throw new NoOrderItemToAddException("No order items to be added.");
        }
        List<OrderItem> orderItems = performAddOrderItems(orderItemCommands);
        order.setOrderItems(orderItems);
        order.calculateTotal();
        order.calculateBalance();
        return order;
    }

    @Override
    public boolean checkIfOrderIdExists(String orderId) {
        return orders.containsKey(orderId);
    }

    @StoreReturn
    protected List<OrderItem> performAddOrderItems(List<OrderItemCommand> orderItemCommands) {
        return orderItemCommands.stream().map(y -> {
            var orderItemToBeAdded = new OrderItem(y.getProductId());
            orderItemToBeAdded.setQuantity(y.getQuantity());
            orderItemToBeAdded.setUnitPrice(y.getUnitPrice());
            return orderItemToBeAdded;
        }).toList();
    }

}
