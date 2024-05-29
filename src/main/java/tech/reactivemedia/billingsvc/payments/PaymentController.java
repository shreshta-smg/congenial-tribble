package tech.reactivemedia.billingsvc.payments;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;

import static io.micronaut.scheduling.TaskExecutors.BLOCKING;

@Controller("/api/payments")
public class PaymentController {
    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Get
    Collection<Payment> getOrders() {
        return paymentRepository.list();
    }

    @ExecuteOn(BLOCKING)
    @Post
    @Status(HttpStatus.CREATED)
    Payment create(@NonNull @Valid @Body PaymentCommand paymentCommand) {
        return paymentRepository.create(paymentCommand);
    }

    @ExecuteOn(BLOCKING)
    @Put
    Payment update(@NotNull @Valid @Body PaymentCommand paymentCommand) {
        return paymentRepository.update(paymentCommand);
    }

    @Get("/{orderId}")
    Payment findOrder(@PathVariable String paymentId) {
        return paymentRepository.find(paymentId);
    }


    @ExecuteOn(BLOCKING)
    @Delete
    @Status(HttpStatus.NO_CONTENT)
    void delete(@NonNull @Valid @Body PaymentCommand paymentCommand) {
        paymentRepository.delete(paymentCommand);
    }
}
