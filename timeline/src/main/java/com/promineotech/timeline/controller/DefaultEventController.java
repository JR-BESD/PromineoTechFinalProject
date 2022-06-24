package com.promineotech.timeline.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.timeline.entity.Event;
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

  /**
   * CONTROLLER: Retrieve a list of Events from Person AND/OR Domain AND/OR Date
   */
  @Override
  public List<Event> fetchEvents(String domain, Long date, String person) {
    log.debug("domain={}&date={}&person={}", domain, date, person);
    return eventService.fetchEvents(domain, date, person);
  }

  /**
   * CONTROLLER: Create A New Event with domain and person relationships
   */
  @Override
  public Event createEvent(Event event) {
    log.debug("Event={}", event);    
    return eventService.createEvent(event);
  }

  /**
   * CONTROLLER: Update an Existing Event description AND/OR date by the Event's name
   */
  @Override
  public void updateEvent(String eventName, String eventDesc, String timelineDate) {
    log.debug("Update Event={}", eventName);
    eventService.updateEvent(eventName, eventDesc, timelineDate);
  }

  /**
   * CONTROLLER: Delete an Event by Name (Cascades to relational table)
   */
  @Override
  public void deleteEvent(String event) {
    log.debug("delete event={}",event);
    eventService.deleteEvent(event);
  }
}