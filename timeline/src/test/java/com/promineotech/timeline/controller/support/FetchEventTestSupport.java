package com.promineotech.timeline.controller.support;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import com.promineotech.timeline.entity.Event;

/**
 * @author JamesR
 *
 */
public class FetchEventTestSupport extends BaseTest{
  protected List<Event> buildExpected() {
    List<Event> list = new LinkedList<>();
    //@formatter:off
    list.add(Event.builder()
        .eventName("First domain")
        .eventDesc("Summer: The massacre at the Wedding of Sergei von Zarovich occurs. "
            + "Barovia forms as the first Ravenloft domain due the actions of Strahd von "
            + "Zarovich. Strahd turns into a vampire.")
        .timelineDate((long) 351)
        .build());     
    list.add(Event.builder()
        .eventName("Duncan MacFarn's death")
        .eventDesc("On the anniversary of their murder, Donal MacFarn and the Ghosts of "
            + "Hospitality possess the stones of Castle Forfarmax and kill the traitor Duncan "
            + "MacFarn and his retinue of servants and guests, leaving Castle Forfarmax empty "
            + "and abandoned for centuries.")
        .timelineDate((long) 351)
        .build());     
  //@formatter:on
   
   Collections.sort(list);
    return list;
  }
  
  /**
   * 
   * @param error
   * @param status
   */
   protected void assertErrorMessageValid(Map<String, Object> error, HttpStatus status) {
     //@formatter:off
     assertThat(error)
     .containsKey("message")
     .containsEntry("status code", status.value())
     .containsEntry("uri", "/events")
     .containsKey("timestamp")
     .containsEntry("reason", status.getReasonPhrase());
     //@formatter:on
   }
}