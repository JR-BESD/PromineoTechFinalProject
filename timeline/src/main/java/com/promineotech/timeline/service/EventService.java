package com.promineotech.timeline.service;

import java.util.List;
import com.promineotech.timeline.entity.Event;
import com.promineotech.timeline.entity.EventRelationship;

/**
 * @author JamesR
 *
 */
public interface EventService {

  /**
   * @param domain
   * @param date
   * @return
   */
  List<Event> fetchEvents(String domain, Long date, String person);

  /**
   * @param event
   * @return 
   * @return
   */
  void deleteEvent(String event);
  
  /**
   * 
   * @param eventRelationship
   * @return
   */
  Event createEvent(EventRelationship eventRelationship);

  /**
   * @param eventRelationship
   * @return
   */
  void updateEvent(String eventName, String eventDesc, String timelineDate);

 


}
