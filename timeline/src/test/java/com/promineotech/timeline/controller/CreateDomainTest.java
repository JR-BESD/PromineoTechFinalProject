package com.promineotech.timeline.controller;

import static org.assertj.core.api.Assertions.assertThat;
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
import com.promineotech.timeline.controller.support.CreateDomainTestSupport;
import com.promineotech.timeline.entity.Domain;

/**
 * @author JamesR
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:flyway/migrations/Timeline_Schema.sql",
    "classpath:flyway/migrations/Timeline_Data.sql"}, config = @SqlConfig(encoding = "utf-8"))
class CreateDomainTest extends CreateDomainTestSupport {

  @Test
  void testCreateDomainReturnsSuccess201() {
    // Given: a domain as JSON
    String body = createDomainBody();
    String uri = getBaseUriForDomains();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> bodyEntity = new HttpEntity<>(body, headers);

    // When: the domain is sent
    ResponseEntity<Domain> response =
        getRestTemplate().exchange(uri, HttpMethod.POST, bodyEntity, Domain.class);

    // Then: a 201 status is returned
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    // And: the returned event is correct

    assertThat(response.getBody()).isNotNull();

    Domain domain = response.getBody();
    
    assertThat(domain.getDomainName()).isEqualTo("Kartakass");
    assertThat(domain.getDomainDesc()).isNotNull();
    assertThat(domain.getRegion()).isEqualTo("The Southern Core");
  }
}