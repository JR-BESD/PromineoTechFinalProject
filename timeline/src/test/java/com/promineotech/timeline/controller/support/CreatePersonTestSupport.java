package com.promineotech.timeline.controller.support;

/**
 * @author JamesR
 *
 */
public class CreatePersonTestSupport extends BaseTest {

  /**
   * @return JSON person insertion
   */
  protected String createPersonBody() {
    //@formatter:off
    String body = "{\n"
        + "   \"name\":\"TEST\",\n"
        + "   \"race\":\"Race\",\n"
        + "   \"description\":\"Description\",\n" 
        + "   \"domain\":\"Barovia\"\n"
        + "}";
    //@formatter:on
    return body;
  }
}