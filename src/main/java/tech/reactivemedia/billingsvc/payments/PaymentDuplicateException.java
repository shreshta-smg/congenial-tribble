package tech.reactivemedia.billingsvc.payments;

public class PaymentDuplicateException extends RuntimeException {
    public PaymentDuplicateException(String paymentId) {
        super("Payment '" + paymentId + "' already exists.");
    }
}
