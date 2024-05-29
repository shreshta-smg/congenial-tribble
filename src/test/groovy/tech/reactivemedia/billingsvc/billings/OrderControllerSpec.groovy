package tech.reactivemedia.billingsvc.billings

import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.runtime.server.EmbeddedServer

import java.time.LocalDate
import java.time.Month
import java.util.stream.StreamSupport

class OrderControllerSpec extends BaseSpec {
    void "test interaction with the OrderController"() {
        given:
        OrderCommand order1 = new OrderCommand(LocalDate.now(), LocalDate.of(2024, Month.MAY, 12))
        String orderId = order1.getOrderId()
        println("ORDERID: ${orderId}")
        Map<String, Object> properties = new HashMap<>(getProperties())
        EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer, properties)
        OrderClient orderClient = embeddedServer.getApplicationContext().getBean(OrderClient)

        when:
        HttpResponse<Order> newOrderResponse = orderClient.create(new OrderCommand(orderId, LocalDate.now(), LocalDate.of(2024, Month.MAY, 8)))
        then:
        HttpStatus.CREATED == newOrderResponse.status
        newOrderResponse.body.isPresent()

        cleanup:
        embeddedServer.close()

    }

    private static int numberOfOrders(OrderClient orderClient) {
        return ordersList(orderClient).size()
    }

    private static List<Order> ordersList(OrderClient orderClient) {
        Iterable<Order> orders = orderClient.allOrders()
        return StreamSupport.stream(orders.spliterator(), false).toList()
    }
}
