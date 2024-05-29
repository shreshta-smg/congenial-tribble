package tech.reactivemedia.billingsvc.billings

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class OrderValidationControllerSpec extends Specification {

    @Inject
    @Client('/')
    HttpClient httpClient

    void "order validation works as expected"() {
        when:
        httpClient.toBlocking()
                .exchange(HttpRequest.POST('/api/orders', new OrderCommand(null,null, null)))

        then:
        def e = thrown(HttpClientResponseException.class)
        e.status == HttpStatus.BAD_REQUEST
    }



}
