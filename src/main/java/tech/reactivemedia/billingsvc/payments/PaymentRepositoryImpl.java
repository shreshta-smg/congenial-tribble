package tech.reactivemedia.billingsvc.payments;

import java.util.Collection;
import java.util.Map;

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

@Singleton
public class PaymentRepositoryImpl implements PaymentRepository {

    private static final Logger log = LoggerFactory.getLogger(PaymentRepositoryImpl.class);
    private final Map<String, Payment> payments;
    public PaymentRepositoryImpl(RootProvider<PaymentContainer> paymentContainerRootProvider) {
        payments = paymentContainerRootProvider.root().getPayments();
    }
    @Override
    public @NonNull Collection<Payment> list() {
        return payments.values();
    }

    @Override
    public @NonNull Payment create(@NonNull @Valid PaymentCommand paymentCmd) {
        if(payments.containsKey(paymentCmd.getPaymentId())) {
            throw new PaymentDuplicateException(paymentCmd.getPaymentId());
        }
        return performCreate(payments, paymentCmd);
    }

    @StoreParams("payments")
    private Payment performCreate(Map<String, Payment> payments, PaymentCommand paymentCmd) {
        return new Payment(paymentCmd.getPaymentId(),
                paymentCmd.getCustomerId(),
                paymentCmd.getOrderId(),
                paymentCmd.getPaymentDate()).setPaymentRef(paymentCmd.getPaymentRef())
                .setAmountPaid(paymentCmd.getAmountPaid())
                .setStaffId(paymentCmd.getStaffId())
                .setRemarks(paymentCmd.getRemarks());
    }

    @Override
    public @Nullable Payment update(@NonNull @Valid PaymentCommand paymentCmd) {
        Payment foundPayment = payments.get(paymentCmd.getPaymentId());
        if(foundPayment != null) {
            return performUpdate(foundPayment, paymentCmd);
        }
        return null;
    }

    @StoreReturn
    private Payment performUpdate(Payment foundPayment, PaymentCommand paymentCmd) {
        return foundPayment.setPaymentRef(paymentCmd.getPaymentRef())
                .setAmountPaid(paymentCmd.getAmountPaid())
                .setStaffId(paymentCmd.getStaffId())
                .setRemarks(paymentCmd.getRemarks());
    }

    @Override
    public @Nullable Payment find(@NonNull @NotBlank String paymentId) {
        return payments.get(paymentId);
    }

    @Override
    public void delete(@NonNull @Valid PaymentCommand paymentCmd) {
        performDelete(paymentCmd);
    }

    @StoreReturn
    private void performDelete(PaymentCommand paymentCmd) {
        String paymentId = paymentCmd.getPaymentId();
        if(payments.remove(paymentId) != null) {
            log.info("Deleted payment with id: {}", paymentId);
        }
        log.info("Unable to deleted payment with id: {}", paymentId);
    }

}
