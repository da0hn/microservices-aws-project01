package dev.da0hn.aws.project01;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class IntegrationTest {

  @Container
  @ServiceConnection
  protected static MariaDBContainer<?> container = new MariaDBContainer<>("mariadb:5.5");

  @SneakyThrows
  protected static String readPayloadRequest(String path) {
    return IOUtils.toString(
      Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(path)),
      StandardCharsets.UTF_8
    );
  }

  @Test
  @DisplayName("Ensure database is running")
  void ensureContainerIsRunning() {
    Assertions.assertThat(container.isRunning()).isTrue();
  }

}
