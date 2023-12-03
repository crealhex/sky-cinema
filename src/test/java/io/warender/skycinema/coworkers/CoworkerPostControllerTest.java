package io.warender.skycinema.coworkers;

import static org.junit.jupiter.api.Assertions.*;

import io.warender.skycinema.auth.UserRole;
import io.warender.skycinema.shared.ApiVersions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CoworkerPostControllerTest {

  @Autowired private TestRestTemplate testRestTemplate;

  @Test
  void coworkerPostSucessTest() {
    var COWORKER_URL = ApiVersions.ONE + "/backoffice/coworkers";
    var coworker = new CreateCoworkerRequest("email", "password", UserRole.ROLE_ADMIN);
    var httpEntity = new HttpEntity<>(coworker);
    ResponseEntity<Coworker> response =
        testRestTemplate.postForEntity(COWORKER_URL, httpEntity, Coworker.class);

    assertEquals(response.getStatusCode(), HttpStatus.CREATED);
  }
}
