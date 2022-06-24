package com.promineotech.timeline.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.doThrow;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import com.promineotech.timeline.controller.support.FetchEventTestSupport;
import com.promineotech.timeline.entity.Event;
import com.promineotech.timeline.service.EventService;

/**
 * @author JamesR
 *
 */

class FetchEventTest extends FetchEventTestSupport {

  @Nested
  @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
  @ActiveProfiles("test")
  @Sql(scripts = {"classpath:flyway/migrations/Timeline_Schema.sql",
      "classpath:flyway/migrations/Timeline_Data.sql"}, config = @SqlConfig(encoding = "utf-8"))
  class TestsThatDoNotPolluteTheApplicationContext extends FetchEventTestSupport {

    @Test
    void testThatEventsAreReturnedWhenAValidDomainAndDateAreSupplied() {
      // Given: a valid domain, date, and URI
      String domain = "Barovia";
      int date = 351;
      String uri = String.format("%s?domain=%s&date=%s", getBaseUriForEvents(), domain, date);

      // When: a connection is made to the URI
      ResponseEntity<List<Event>> response = getRestTemplate().exchange(uri, HttpMethod.GET, null,
          new ParameterizedTypeReference<>() {});

      // Then: a success (OK - 200) status code is returned
      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

      // AND: the actual list is the same as the expected list
      List<Event> actual = response.getBody();
      List<Event> expected = buildExpected();

      assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testThatAnErrorIsReturnedWhenUnknownDateSupplied() {
      // Given: a valid domain(name), invalid date, and URI
      String domain = "Barovia";
      int date = 0;
      String uri = String.format("%s?domain=%s&date=%s", getBaseUriForEvents(), domain, date);

      // When: a connection is made to the URI
      ResponseEntity<Map<String, Object>> response = getRestTemplate().exchange(uri, HttpMethod.GET,
          null, new ParameterizedTypeReference<>() {});

      // Then: a not found(404) status code is returned
      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

      // AND: an error message is returned
      Map<String, Object> error = response.getBody();
      assertErrorMessageValid(error, HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @MethodSource("com.promineotech.timeline.controller.FetchEventTest#parametersForInvalidInput")
    void testThatAnErrorIsReturnedWhenInvalidValueSupplied(String domain, String date,
        String reason) {
      // Given: a valid domain, invalid date, and URI
      String uri = String.format("%s?domain=%s&date=%s", getBaseUriForEvents(), domain, date);

      // When: a connection is made to the URI
      ResponseEntity<Map<String, Object>> response = getRestTemplate().exchange(uri, HttpMethod.GET,
          null, new ParameterizedTypeReference<>() {});

      // Then: a not found(404) status code is returned
      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

      // AND: an error message is returned
      Map<String, Object> error = response.getBody();
      assertErrorMessageValid(error, HttpStatus.BAD_REQUEST);
    }
  }

  static Stream<Arguments> parametersForInvalidInput() {
    return Stream.of(arguments("A".repeat(31), "351", "domain length is too long"),
        arguments("Barovia", "351 BC", "date must be a valid integer"));
  }

  @Nested
  @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
  @ActiveProfiles("test")
  @Sql(scripts = {"classpath:flyway/migrations/Timeline_Schema.sql",
      "classpath:flyway/migrations/Timeline_Data.sql"}, config = @SqlConfig(encoding = "utf-8"))
  class TestsThatDoPolluteTheApplicationContext extends FetchEventTestSupport {
    @MockBean
    private EventService eventService;

    @Test
    void testThatAnUnplannedErrorResults500() {
      // Given: a valid domain(name), invalid date, and URI
      String domain = "Barovia";
      Long date = 351L;
      String person = "Count Strahd von Zarovich";
      String uri = String.format("%s?domain=%s&date=%s&person=%s", getBaseUriForEvents(), domain,
          date, person);

      doThrow(new RuntimeException("Doh!")).when(eventService).fetchEvents(domain, date, person);

      // When: a connection is made to the URI
      ResponseEntity<Map<String, Object>> response = getRestTemplate().exchange(uri, HttpMethod.GET,
          null, new ParameterizedTypeReference<>() {});

      // Then: an Internal Server Error(500) status code is returned
      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

      // AND: an error message is returned
      Map<String, Object> error = response.getBody();
      assertErrorMessageValid(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}