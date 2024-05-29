package tech.reactivemedia.billingsvc.orders;

public class OrderDuplicateException extends RuntimeException {
    public OrderDuplicateException(String orderId) {
        super("Order '" + orderId + "' already exists.");
    }
}
