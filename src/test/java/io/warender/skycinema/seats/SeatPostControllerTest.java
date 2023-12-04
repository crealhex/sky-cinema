package io.warender.skycinema.seats;

import static org.junit.jupiter.api.Assertions.*;

import io.warender.skycinema.screening_rooms.ScreeningRoom;
import io.warender.skycinema.screening_rooms.ScreeningRoomStatus;
import io.warender.skycinema.screening_rooms.ScreeningRoomStorage;
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
class SeatPostControllerTest {

  @Autowired private TestRestTemplate testRestTemplate;

  @Autowired private ScreeningRoomStorage screeningRoomStorage;

  @BeforeEach
  void cleanUp() {
    screeningRoomStorage.deleteAll();
  }

  @Test
  void createSeatAndAttachToScreeningRoom() {
    var screeningRoom = new ScreeningRoom();
    screeningRoom.setMaxCapacity(7);
    screeningRoom.setSeatsPerRow(3);
    screeningRoom.setStatus(ScreeningRoomStatus.OPEN);
    screeningRoom = screeningRoomStorage.save(screeningRoom);

    var SEATS_URL =
        ApiVersions.ONE + "/backoffice/screening-rooms/" + screeningRoom.getId() + "/seats";
    var seats =
        Set.of(
            new SeatPostController.Request("A", 1),
            new SeatPostController.Request("A", 2),
            new SeatPostController.Request("A", 3));
    var response = testRestTemplate.postForEntity(SEATS_URL, seats, ArrayList.class);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  void throwRepeatedSeatInScreeningRoomMessage() {
    var screeningRoom = new ScreeningRoom();
    screeningRoom.setMaxCapacity(7);
    screeningRoom.setSeatsPerRow(3);
    screeningRoom.setStatus(ScreeningRoomStatus.OPEN);
    screeningRoom = screeningRoomStorage.save(screeningRoom);

    var SEATS_URL =
        ApiVersions.ONE + "/backoffice/screening-rooms/" + screeningRoom.getId() + "/seats";
    var seats =
        List.of(new SeatPostController.Request("A", 1), new SeatPostController.Request("A", 1));
    var response = testRestTemplate.postForEntity(SEATS_URL, seats, ProblemDetail.class);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
  }

  @Test
  void throwScreeningCapacityIsFullMessage() {
    var screeningRoom = new ScreeningRoom();
    screeningRoom.setMaxCapacity(3);
    screeningRoom.setSeatsPerRow(3);
    screeningRoom.setStatus(ScreeningRoomStatus.OPEN);
    screeningRoom = screeningRoomStorage.save(screeningRoom);

    var SEATS_URL =
        ApiVersions.ONE + "/backoffice/screening-rooms/" + screeningRoom.getId() + "/seats";
    var seats =
        List.of(
            new SeatPostController.Request("A", 1),
            new SeatPostController.Request("A", 2),
            new SeatPostController.Request("A", 3));
    testRestTemplate.postForEntity(SEATS_URL, seats, ArrayList.class);

    var additionalSeat = List.of(new SeatPostController.Request("A", 4));
    var response = testRestTemplate.postForEntity(SEATS_URL, additionalSeat, ProblemDetail.class);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
  }

  @Test
  void givenMoreSeatsThanAllowed_thenErrorShouldBeThrown() {
    var screeningRoom = new ScreeningRoom();
    screeningRoom.setMaxCapacity(3);
    screeningRoom.setSeatsPerRow(3);
    screeningRoom.setStatus(ScreeningRoomStatus.OPEN);
    screeningRoom = screeningRoomStorage.save(screeningRoom);

    var SEATS_URL =
        ApiVersions.ONE + "/backoffice/screening-rooms/" + screeningRoom.getId() + "/seats";
    var seats =
        List.of(
            new SeatPostController.Request("A", 1),
            new SeatPostController.Request("A", 2),
            new SeatPostController.Request("A", 3),
            new SeatPostController.Request("A", 4));
    var response = testRestTemplate.postForEntity(SEATS_URL, seats, ArrayList.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
}
