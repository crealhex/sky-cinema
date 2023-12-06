package io.warender.skycinema.cinema_halls;

import static org.junit.jupiter.api.Assertions.*;

import io.warender.skycinema.shared.ApiVersions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CinemaHallPostControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  void createScreeningRoom() {
    var CINEMA_HALLS_URL = ApiVersions.ONE + "/backoffice/cinema-halls";
    var screeningRoom = new CinemaHallPostController.Request(7, 3, CinemaHallStatus.OPEN);
    var response = testRestTemplate.postForEntity(CINEMA_HALLS_URL, screeningRoom, CinemaHall.class);
    assertEquals(response.getStatusCode(), HttpStatus.CREATED);
  }
}
