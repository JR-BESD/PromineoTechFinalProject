package com.promineotech.timeline.entity;

import java.util.Comparator;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JamesR
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event implements Comparable<Event>{  
  private Long eventId;
  private String eventName;
  private String eventDesc;
  private Long timelineDate;
  private List<String> domains;
  private List<String> people;  
    
  @JsonIgnore
  public Long getEventId() {
    return eventId;
  }

  @Override
  public int compareTo(Event that) {  
    //@formatter:off
    return Comparator
        .comparing(Event::getTimelineDate)
        .thenComparing(Event::getEventName)
        .thenComparing(Event::getEventDesc)
        .compare(this, that);
    //@formatter:on
  }
}
