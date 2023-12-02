package io.warender.skycinema.customers;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.warender.skycinema.auth.UserRole;
import io.warender.skycinema.coworkers.Coworker;
import io.warender.skycinema.coworkers.CreateCoworkerRequest;
import io.warender.skycinema.shared.ApiVersions;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public final class CustomerPostController {

  private final CustomerStorage customerStorage;

  @Tag(name = "Authentication")
  @PostMapping(ApiVersions.ONE + "/signup")
  public ResponseEntity<Customer> registerCustomer(@RequestBody CreateCustomerRequest request) {
    log.info("[SYSTEM] Creating customer");
    var customer = new Customer(request.email(), request.password(), UserRole.ROLE_CUSTOMER, false);
    log.info("[SYSTEM] Customer created");
    return ResponseEntity.status(HttpStatus.CREATED).body(customerStorage.save(customer));
  }
}
