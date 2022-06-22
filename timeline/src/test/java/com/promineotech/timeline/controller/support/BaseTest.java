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
  
//  /**
//   * 
//   * @return
//   */
//  protected String getBaseUriForTimline() {
//    return String.format("http://localhost:%d/timeline", serverPort);
//  }
  
  /**
   * 
   * @return
   */
  protected String getBaseUriForEvents() {
    return String.format("http://localhost:%d/events", serverPort);
  }
   
  /**
   * 
   * @return
   */
  protected String getBaseUriForDomains() {
    return String.format("http://localhost:%d/domains", serverPort);
  }
  
  /**
   * @return
   */
  protected String getBaseUriForPeople() {
    return String.format("http://localhost:%d/people", serverPort);
  }
}
