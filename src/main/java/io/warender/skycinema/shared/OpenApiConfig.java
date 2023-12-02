package io.warender.skycinema.shared;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

public final class OpenApiConfig {

  @Bean
  public OpenAPI supplierPortalApiDocs() {
    return new OpenAPI()
        .info(new Info().title("Sky Cinema").version("v1.0.0"))
        .addServersItem(
            new Server().url("http://localhost:8080/api/v1").description("Local server"))
        .addTagsItem(new Tag().name("Authentication"));
  }

  @Bean
  public GroupedOpenApi ecommerceApi() {
    return GroupedOpenApi.builder().group("Cinema").pathsToMatch("/**").build();
  }
}
