package tech.reactivemedia.billingsvc.payments;

import java.util.Collection;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public interface PaymentRepository {

    @NonNull
    Collection<Payment> list();

    @NonNull
    Payment create(@NonNull @Valid PaymentCommand paymentCmd);

    @Nullable
    Payment update(@NonNull @Valid PaymentCommand paymentCmd);

    @Nullable
    Payment find(@NonNull @NotBlank String paymentId);

    void delete(@NonNull @Valid PaymentCommand paymentCmd);
}
