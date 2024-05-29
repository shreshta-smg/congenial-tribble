package tech.reactivemedia.billingsvc.billings

import io.micronaut.core.annotation.NonNull
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.client.annotation.Client
import jakarta.validation.Valid

@Client("/api/orders")
interface OrderClient {
    @Get
    Iterable<Order> allOrders();

    @Get("/{orderId}")
    Optional<Order> find(@NonNull @PathVariable String orderId);

    @Post
    HttpResponse<Order> create(@NonNull @Valid @Body OrderCommand orderCommand);

    @Put
    Optional<Order> update(@NonNull @Valid @Body OrderCommand orderCommand);

    @NonNull
    @Delete
    HttpStatus delete(@NonNull @Valid @Body OrderCommand orderCommand);

}
