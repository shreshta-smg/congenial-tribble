package tech.reactivemedia.billingsvc.orders;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.List;

import static io.micronaut.scheduling.TaskExecutors.BLOCKING;

@Controller("/api/orders")
public class OrderController {
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Get
    Collection<Order> getOrders() {
        return orderRepository.list();
    }

    @Get("/{orderId}/exists")
    HttpResponse<Boolean> isOrderExists(@PathVariable String orderId) {
        return HttpResponse.ok().body(orderRepository.checkIfOrderIdExists(orderId));
    }

    @ExecuteOn(BLOCKING)
    @Post
    @Status(HttpStatus.CREATED)
    Order create(@NonNull @Valid @Body OrderCommand orderCommand) {
        return orderRepository.create(orderCommand);
    }

    @ExecuteOn(BLOCKING)
    @Put
    Order update(@NotNull @Valid @Body OrderCommand orderCommand) {
        return orderRepository.update(orderCommand);
    }

    @Get("/{orderId}")
    Order findOrder(@PathVariable String orderId) {
        return orderRepository.find(orderId);
    }

    @ExecuteOn(BLOCKING)
    @Put("/{orderId}")
    Order addOrderItems(@PathVariable String orderId,
            @NonNull @Valid @Body List<OrderItemCommand> orderItemCommands) {
        return orderRepository.addOrderItems(orderId, orderItemCommands);
    }

    @ExecuteOn(BLOCKING)
    @Delete
    @Status(HttpStatus.NO_CONTENT)
    void delete(@NonNull @Valid @Body OrderCommand orderCommand) {
        orderRepository.delete(orderCommand);
    }
}
