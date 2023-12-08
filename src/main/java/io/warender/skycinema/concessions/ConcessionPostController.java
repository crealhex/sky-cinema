package io.warender.skycinema.concessions;

import io.warender.skycinema.shared.ApiVersions;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public final class ConcessionPostController {

  private final ConcessionStorage concessionStorage;

  @PostMapping(ApiVersions.ONE + "/backoffice/concessions")
  public ResponseEntity<Concession> createConcession(@RequestBody Request request) {
    var concession = new Concession();
    concession.setName(request.name());
    concession.setCategory(request.category());
    concession.setPriceCents(request.priceCents());
    concession.setVegetarian(request.vegetarian());
    var createdConcession = concessionStorage.save(concession);
    return ResponseEntity.ok(createdConcession);
  }

  public record Request(
    String name,
    ConcessionCategory category,
    int priceCents,
    boolean vegetarian
  ) {}
}
