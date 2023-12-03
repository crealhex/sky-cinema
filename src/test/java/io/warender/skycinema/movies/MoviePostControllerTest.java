package io.warender.skycinema.movies;

import static org.junit.jupiter.api.Assertions.*;

import io.warender.skycinema.shared.ApiVersions;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoviePostControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  void moviePostSuccessTest() {
    var MOVIE_URL = ApiVersions.ONE + "/backoffice/movies";
    var movie =
        new MoviePostController.Request("Movie Title", Set.of("es", "en"), 140, 18, MovieStatus.RELEASED);
    var httpEntity = new HttpEntity<>(movie);
    var response = testRestTemplate.postForEntity(MOVIE_URL, httpEntity, Movie.class);

    assertEquals(response.getStatusCode(), HttpStatus.CREATED);
  }
}
