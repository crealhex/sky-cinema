package io.warender.skycinema.coworkers;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.warender.skycinema.shared.ApiVersions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public final class CoworkerPostController {

  private final CoworkerStorage coworkerStorage;

  @Tag(name = "Coworkers")
  @PostMapping(ApiVersions.ONE + "/backoffice/coworkers")
  public ResponseEntity<Coworker> createCoworker(@RequestBody CreateCoworkerRequest request) {
    log.info("[SYSTEM] Creating coworker");
    var coworker = new Coworker(request.email(), request.password(), request.role(), true);
    log.info("[SYSTEM] Coworker created");
    return ResponseEntity.status(HttpStatus.CREATED).body(coworkerStorage.save(coworker));
  }
}
