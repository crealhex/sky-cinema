package io.warender.skycinema.orders;

import io.warender.skycinema.shared.ApiVersions;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public final class OrdersGetController {

  private final OrderStorage orderStorage;
  private final OrderTimer orderTimer;

  @GetMapping(ApiVersions.ONE + "/orders")
  public ResponseEntity<Map<String, Object>> getOrders(@RequestBody Request request) {
    var order = createIfNotExists(request.clientSessionId(), request.movieSessionId());
    return ResponseEntity.ok(Map.of("order", order));
  }

  private Order createIfNotExists(String clientSessionId, UUID movieSessionId) {
    return orderStorage.findByClientSessionId(clientSessionId).orElseGet(() -> {
      var order = new Order();
      order.setClientSessionId(clientSessionId);
      order.setMovieSessionId(movieSessionId);
      order.setTotalAmountCents(0);
      order.setCreatedAt(Instant.now());
      order.setStatus(OrderStatus.ONGOING);
      orderTimer.startCountingTime(order);
      return orderStorage.save(order);
    });
  }

  public record Request(String clientSessionId, UUID movieSessionId) {}
}
