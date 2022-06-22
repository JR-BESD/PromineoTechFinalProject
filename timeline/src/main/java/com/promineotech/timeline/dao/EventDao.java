package com.promineotech.timeline.dao;

import java.util.List;
import java.util.Optional;
import com.promineotech.timeline.entity.Event;

/**
 * @author JamesR
 *
 */
public interface EventDao {

  /**
   * @param domain
   * @param date
   * @return
   */
  List<Event> fetchEvents(String domain, Long date, String person);

  /**
   * @param event
   * @return
   */
  void deleteEvent(String event);
  

  /**
   * @param event
   * @return
   */
  Event saveEvent(String eventName, String eventDesc, Long timelineDate, List<String> domains,
      List<String> people);

  /**
   * @param domains
   * @return
   */
  List<String> fetchDomains(List<String> domainIds);

  /**
   * @param people
   * @return
   */
  List<String> fetchPeople(List<String> people);

  /**
   * @param eventName
   * @return
   */
  Optional<String> fetchEventName(String eventName);

  /**
   * @param eventDesc
   * @return
   */
  Optional<String> fetchEventDesc(String eventDesc);

  /**
   * @param timelineDate
   * @return
   */
  Optional<Long> fetchTimelineDate(Long timelineDate);

  /**
   * 
   * @param eventName
   * @param eventDesc
   * @param timelineDate
   */
  void updateEvent(String eventName, String eventDesc, String timelineDate);



  
  
}
