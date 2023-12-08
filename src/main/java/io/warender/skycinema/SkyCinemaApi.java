package io.warender.skycinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SkyCinemaApi {

  public static void main(String... args) {
    SpringApplication.run(SkyCinemaApi.class, args);
  }
}
