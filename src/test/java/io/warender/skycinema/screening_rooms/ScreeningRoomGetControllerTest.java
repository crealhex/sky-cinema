package io.warender.skycinema.screening_rooms;

import static org.junit.jupiter.api.Assertions.*;

import io.warender.skycinema.shared.ApiVersions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ScreeningRoomGetControllerTest {

  private final static String SCREENING_ROOMS_URL = ApiVersions.ONE + "/backoffice/screening-rooms";

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Autowired
  private ScreeningRoomStorage screeningRoomStorage;


  @BeforeEach
  void cleanUp() {
    screeningRoomStorage.deleteAll();
  }

  @Test
  void givenScreeningRoomId_thenReturnScreemingRoomEntity() {
    var screeningRoom = new ScreeningRoom();
    screeningRoom.setMaxCapacity(100);
    screeningRoom.setStatus(ScreeningRoomStatus.OPEN);
    screeningRoom = screeningRoomStorage.save(screeningRoom);

    var response =
        testRestTemplate.getForEntity(SCREENING_ROOMS_URL + "/" + screeningRoom.getId(), ScreeningRoom.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertNotNull(response.getBody());
  }

  @Test
  void givenScreeningRoomId_whenScreeningRoomNotFound_thenReturnNotFound() {
    var response =
        testRestTemplate.getForEntity(SCREENING_ROOMS_URL + "/999999", ProblemDetail.class);

    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
  }
}
