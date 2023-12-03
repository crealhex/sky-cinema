package io.warender.skycinema.shared;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class FlywayConfig {

  private final Environment environment;

  @Bean(initMethod = "migrate")
  public Flyway flyway() {
    if (environment.getRequiredProperty("spring.flyway.enabled").equals("false")) {
      return null;
    }
    return Flyway.configure()
        .baselineOnMigrate(true)
        .dataSource(
            environment.getRequiredProperty("spring.datasource.url"),
            environment.getRequiredProperty("spring.datasource.username"),
            environment.getRequiredProperty("spring.datasource.password"))
        .locations("classpath:db/migration")
        .load();
  }
}
