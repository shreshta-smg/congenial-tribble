package tech.reactivemedia.billingsvc.billings

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject

import java.time.LocalDate
import java.time.Month

@MicronautTest
class OrderDuplicationExceptionSpec extends BaseSpec {
    @Inject
    @Client("/")
    HttpClient httpClient

    void "duplicated fruit returns 422"() {
        when:
        OrderCommand banana = new OrderCommand(
                OrderId.createOrderId().getOrderId(), LocalDate.now(), LocalDate.of(2024, Month.MAY, 12)
        )
        HttpRequest<?> request = HttpRequest.POST("/api/orders", banana)
        HttpResponse<?> response = httpClient.toBlocking().exchange(request)

        then:
        HttpStatus.CREATED == response.status()

        when:
        httpClient.toBlocking().exchange(request)

        then:
        HttpClientResponseException e = thrown()
        HttpStatus.UNPROCESSABLE_ENTITY == e.status

        when:
        HttpRequest<?> deleteRequest = HttpRequest.DELETE("/api/orders", banana)
        HttpResponse<?> deleteResponse = httpClient.toBlocking().exchange(deleteRequest)

        then:
        HttpStatus.NO_CONTENT == deleteResponse.status()
    }
}
