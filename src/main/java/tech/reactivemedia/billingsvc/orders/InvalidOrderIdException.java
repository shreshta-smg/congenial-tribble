package tech.reactivemedia.billingsvc.orders;

public class InvalidOrderIdException extends RuntimeException {
    public InvalidOrderIdException(String orderId) {
        super("Order '" + orderId + "' is invalid.");
    }
}
