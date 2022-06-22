package com.promineotech.timeline.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.timeline.entity.Event;
import com.promineotech.timeline.entity.EventRelationship;
import com.promineotech.timeline.service.EventService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JamesR
 *
 */
@RestController
@Slf4j
public class DefaultEventController implements EventController {

  @Autowired
  private EventService eventService;

  @Override
  public List<Event> fetchEvents(String domain, Long date, String person) {
    log.debug("domain={}&date={}&person={}", domain, date, person);
    return eventService.fetchEvents(domain, date, person);
  }

  @Override
  public void deleteEvent(String event) {
    log.debug("delete event={}",event);
    eventService.deleteEvent(event);
  }
  
  @Override
  public Event createEvent(EventRelationship eventRelationship) {
    log.debug("Event={}", eventRelationship);    
    return eventService.createEvent(eventRelationship);
  }

  @Override
  public void updateEvent(String eventName, String eventDesc, String timelineDate) {
    log.debug("Update Event={}", eventName);
    eventService.updateEvent(eventName, eventDesc, timelineDate);
  }

}
