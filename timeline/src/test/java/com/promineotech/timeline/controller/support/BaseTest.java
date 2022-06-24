package com.promineotech.timeline.controller.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import lombok.Getter;

/**
 * @author JamesR
 *
 */
public class BaseTest {
  @LocalServerPort
  private int serverPort;
  
  @Autowired
  @Getter
  private TestRestTemplate restTemplate;
 
  /** 
   * @return events uri
   */
  protected String getBaseUriForEvents() {
    return String.format("http://localhost:%d/events", serverPort);
  }
   
  /**
   * @return domains uri
   */
  protected String getBaseUriForDomains() {
    return String.format("http://localhost:%d/domains", serverPort);
  }
  
  /**
   * @return people uri
   */
  protected String getBaseUriForPeople() {
    return String.format("http://localhost:%d/people", serverPort);
  }
}