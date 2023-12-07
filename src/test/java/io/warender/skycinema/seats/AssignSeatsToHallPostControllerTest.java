package io.warender.skycinema.seats;

import static org.junit.jupiter.api.Assertions.*;

import io.warender.skycinema.cinema_halls.AssignSeatsToHallPostController;
import io.warender.skycinema.cinema_halls.CinemaHall;
import io.warender.skycinema.cinema_halls.CinemaHallStatus;
import io.warender.skycinema.cinema_halls.CinemaHallStorage;
import io.warender.skycinema.shared.ApiVersions;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AssignSeatsToHallPostControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Autowired
  private CinemaHallStorage cinemaHallStorage;

  @BeforeEach
  void cleanUp() {
    cinemaHallStorage.deleteAll();
  }

  @Test
  void createSeatAndAttachToScreeningRoom() {
    var cinemaHall = new CinemaHall();
    cinemaHall.setName("SALA 1");
    cinemaHall.setMaxCapacity(7);
    cinemaHall.setSeatsPerRow(3);
    cinemaHall.setStatus(CinemaHallStatus.OPEN);
    cinemaHall = cinemaHallStorage.save(cinemaHall);

    var SEATS_URL =
        ApiVersions.ONE + "/backoffice/cinema-halls/" + cinemaHall.getId() + "/seats";
    var seats =
        Set.of(
            new AssignSeatsToHallPostController.Request("A", 1),
            new AssignSeatsToHallPostController.Request("A", 2),
            new AssignSeatsToHallPostController.Request("A", 3));
    var response = testRestTemplate.postForEntity(SEATS_URL, seats, ArrayList.class);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  void throwRepeatedSeatInScreeningRoomMessage() {
    var cinemaHall = new CinemaHall();
    cinemaHall.setName("SALA 1");
    cinemaHall.setMaxCapacity(7);
    cinemaHall.setSeatsPerRow(3);
    cinemaHall.setStatus(CinemaHallStatus.OPEN);
    cinemaHall = cinemaHallStorage.save(cinemaHall);

    var SEATS_URL =
        ApiVersions.ONE + "/backoffice/cinema-halls/" + cinemaHall.getId() + "/seats";
    var seats =
        List.of(new AssignSeatsToHallPostController.Request("A", 1), new AssignSeatsToHallPostController.Request("A", 1));
    var response = testRestTemplate.postForEntity(SEATS_URL, seats, ProblemDetail.class);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
  }

  @Test
  void throwScreeningCapacityIsFullMessage() {
    var cinemaHall = new CinemaHall();
    cinemaHall.setName("SALA 1");
    cinemaHall.setMaxCapacity(3);
    cinemaHall.setSeatsPerRow(3);
    cinemaHall.setStatus(CinemaHallStatus.OPEN);
    cinemaHall = cinemaHallStorage.save(cinemaHall);

    var SEATS_URL =
        ApiVersions.ONE + "/backoffice/cinema-halls/" + cinemaHall.getId() + "/seats";
    var seats =
        List.of(
            new AssignSeatsToHallPostController.Request("A", 1),
            new AssignSeatsToHallPostController.Request("A", 2),
            new AssignSeatsToHallPostController.Request("A", 3));
    testRestTemplate.postForEntity(SEATS_URL, seats, ArrayList.class);

    var additionalSeat = List.of(new AssignSeatsToHallPostController.Request("A", 4));
    var response = testRestTemplate.postForEntity(SEATS_URL, additionalSeat, ProblemDetail.class);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
  }

  @Test
  void givenMoreSeatsThanAllowed_thenErrorShouldBeThrown() {
    var cinemaHall = new CinemaHall();
    cinemaHall.setName("SALA 1");
    cinemaHall.setMaxCapacity(3);
    cinemaHall.setSeatsPerRow(3);
    cinemaHall.setStatus(CinemaHallStatus.OPEN);
    cinemaHall = cinemaHallStorage.save(cinemaHall);

    var SEATS_URL =
        ApiVersions.ONE + "/backoffice/cinema-halls/" + cinemaHall.getId() + "/seats";
    var seats =
        List.of(
            new AssignSeatsToHallPostController.Request("A", 1),
            new AssignSeatsToHallPostController.Request("A", 2),
            new AssignSeatsToHallPostController.Request("A", 3),
            new AssignSeatsToHallPostController.Request("A", 4));
    var response = testRestTemplate.postForEntity(SEATS_URL, seats, ArrayList.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
}
