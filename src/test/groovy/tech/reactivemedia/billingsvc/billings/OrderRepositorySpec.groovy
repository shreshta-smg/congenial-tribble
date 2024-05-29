package tech.reactivemedia.billingsvc.billings

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.validation.ConstraintViolationException
import spock.lang.Specification
import jakarta.inject.Inject

@MicronautTest(startApplication = false)
class OrderRepositorySpec extends Specification {

    @Inject
    OrderRepository orderRepository

    void "methods validate parameters"() {
        when:
        orderRepository.create(new OrderCommand( null, null))

        then:
        thrown(ConstraintViolationException)
    }

}
