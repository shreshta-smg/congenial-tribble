package tech.reactivemedia.billingsvc.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoOrderItemToAddException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(NoOrderItemToAddException.class);

    public NoOrderItemToAddException(String s) {
        log.error(s);
    }
}
