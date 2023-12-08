package io.warender.skycinema.concessions;

import io.warender.skycinema.shared.ApiVersions;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public final class ConcessionsGetController {

  private final ConcessionStorage concessionStorage;

  @GetMapping(ApiVersions.ONE + "/concessions")
  public ResponseEntity<List<Concession>> getConcession() {
    return ResponseEntity.ok(concessionStorage.findAll());
  }
}
