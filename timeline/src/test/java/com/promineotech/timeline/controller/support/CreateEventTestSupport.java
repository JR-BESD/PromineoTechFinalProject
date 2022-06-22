package com.promineotech.timeline.controller.support;

/**
 * @author JamesR
 *
 */
public class CreateEventTestSupport extends BaseTest {

  /**
   * 
   * @return
   */
  protected String createEventBody() {
    //@formatter:off
    String body = "{\n"
        + "   \"eventName\":\"Strahd dominates\",\n"
        + "   \"eventDesc\":\"Strahd flexes his muscles to show domination over Barovia.\",\n"
        + "   \"timelineDate\":\"352\",\n"
        + "   \"domainLord\":\"Count Strahd von Zarovich\",\n"
        + "   \"domains\":[\n"
        + "      \"Barovia\"\n"
        + "   ],\n"
        + "   \"people\":[\n"
        + "      \"Count Strahd von Zarovich\"\n"
        + "   ]\n"
        + "}";                
    //@formatter:on
    return body;
  }

}
