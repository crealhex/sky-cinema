package io.warender.skycinema.customers;

import static org.junit.jupiter.api.Assertions.*;

import io.warender.skycinema.shared.ApiVersions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerPostControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  void customerPostSuccessTest() {
    var CUSTOMER_URL = ApiVersions.ONE + "/signup";
    var customer = new CreateCustomerRequest("email", "password");
    var httpEntity = new HttpEntity<>(customer);
    ResponseEntity<Customer> response =
      testRestTemplate.postForEntity(CUSTOMER_URL, httpEntity, Customer.class);

    assertEquals(response.getStatusCode(), HttpStatus.CREATED);
  }
}
