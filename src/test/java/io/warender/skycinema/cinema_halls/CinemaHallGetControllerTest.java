package io.warender.skycinema.cinema_halls;

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
class CinemaHallGetControllerTest {

  private final static String CINEMA_HALLS_URL = ApiVersions.ONE + "/backoffice/cinema-halls";

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Autowired
  private CinemaHallStorage cinemaHallStorage;


  @BeforeEach
  void cleanUp() {
    cinemaHallStorage.deleteAll();
  }

  @Test
  void givenCinemaHallId_thenReturnScreemingRoomEntity() {
    var screeningRoom = new CinemaHall();
    screeningRoom.setMaxCapacity(100);
    screeningRoom.setStatus(CinemaHallStatus.OPEN);
    screeningRoom = cinemaHallStorage.save(screeningRoom);

    var response =
        testRestTemplate.getForEntity(CINEMA_HALLS_URL + "/" + screeningRoom.getId(), CinemaHall.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertNotNull(response.getBody());
  }

  @Test
  void givenCinemaHallId_whenScreeningRoomNotFound_thenReturnNotFound() {
    var response =
        testRestTemplate.getForEntity(CINEMA_HALLS_URL + "/999999", ProblemDetail.class);

    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
  }
}
