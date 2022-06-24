package com.promineotech.timeline.controller;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import com.promineotech.timeline.controller.support.CreateEventTestSupport;
import com.promineotech.timeline.entity.Event;

/**
 * @author JamesR
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:flyway/migrations/Timeline_Schema.sql",
    "classpath:flyway/migrations/Timeline_Data.sql"}, config = @SqlConfig(encoding = "utf-8"))
class CreateEventTest extends CreateEventTestSupport {

  @Test
  void testCreateEventReturnsSuccess201() {
    // Given: an event as JSON
    String body = createEventBody();
    String uri = getBaseUriForEvents();
    
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    
    HttpEntity<String> bodyEntity = new HttpEntity<>(body, headers);    
   

    // When: the event is sent
    ResponseEntity<Event> response =
        getRestTemplate().exchange(uri, HttpMethod.POST, bodyEntity, Event.class);

    // Then: a 201 status is returned
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    // And: the returned event is correct
    assertThat(response.getBody()).isNotNull();
    
    Event event = response.getBody();
    List<String> people = event.getPeople();
    List<String> domains = event.getDomains();
    String person = people.get(0);
    System.out.println(people);
    String domain = domains.get(0);
    System.out.println(domains);
    
    assertThat(event.getEventName()).isEqualTo("Strahd dominates");  
    assertThat(domain == "Barovia");
    assertThat(person == "Count Strahd von Zarovich");     
  }
}