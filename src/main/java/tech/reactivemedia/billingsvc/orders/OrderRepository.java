package tech.reactivemedia.billingsvc.orders;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.Collection;
import java.util.List;

public interface OrderRepository {
    @NonNull
    Collection<Order> list();

    @NonNull
    Order create(@NonNull @Valid OrderCommand order)
            throws OrderDuplicateException;

    @Nullable
    Order update(@NonNull @Valid OrderCommand order);

    @Nullable
    Order find(@NonNull @NotBlank String orderId);

    void delete(@NonNull @Valid OrderCommand order);

    Order addOrderItems(@NonNull @NotBlank String orderId, @NonNull @Valid List<OrderItemCommand> orderItemCommands);
    boolean checkIfOrderIdExists(@NonNull @NotBlank String orderId);
}
