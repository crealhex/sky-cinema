package io.warender.skycinema.screening_rooms;

import static org.junit.jupiter.api.Assertions.*;

import io.warender.skycinema.shared.ApiVersions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ScreeningRoomPostControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  void createScreeningRoom() {
    var SCREENING_ROOMS_URL = ApiVersions.ONE + "/backoffice/screening-rooms";
    var screeningRoom = new ScreeningRoomPostController.Request(7, 3, ScreeningRoomStatus.OPEN);
    var response = testRestTemplate.postForEntity(SCREENING_ROOMS_URL, screeningRoom, ScreeningRoom.class);
    assertEquals(response.getStatusCode(), HttpStatus.CREATED);
  }
}
