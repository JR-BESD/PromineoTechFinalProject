package com.promineotech.timeline.controller.support;

/**
 * @author JamesR
 *
 */
public class CreatePersonTestSupport extends BaseTest {

  /**
   * 
   * @return
   */
  protected String createPersonBody() {
    //@formatter:off
    String body = "{\n"
        + "   \"name\":\"Some Name\",\n"
        + "   \"race\":\"Some Race\",\n"
        + "   \"description\":\"Some Description\",\n" 
        + "   \"domain\":\"Barovia\"\n"
        + "}";
    //@formatter:on
    return body;

  }
}
