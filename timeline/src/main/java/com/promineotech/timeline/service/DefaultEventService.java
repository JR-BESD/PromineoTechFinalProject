package com.promineotech.timeline.service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.promineotech.timeline.dao.EventDao;
import com.promineotech.timeline.entity.Event;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JamesR
 *
 */
@Service
@Slf4j
public class DefaultEventService implements EventService {

  @Autowired
  private EventDao eventDao;
  
  /**
   * SERVICE: Retrieve a list of Events from Person AND/OR Domain AND/OR Date
   */
  @Transactional(readOnly = true)
  @Override
  public List<Event> fetchEvents(String domain, Long date, String person) {
    log.info("The fetchEvents method was called with domain={} and date = {} and person=%s", domain, date, person);

    List<Event> events = eventDao.fetchEvents(domain, date, person);

    if (events.isEmpty()) {
      String msg = String.format("No events found for domain=%s and date=%d and person=%s", domain, date, person);
      throw new NoSuchElementException(msg);
    }

    Collections.sort(events);
    return events;
  }
  
  /**
   * SERVICE: Create A New Event with domain and person relationships
   */
  @Transactional
  @Override
  public Event createEvent(Event event) {
    String eventName = getEventName(event);
  
    String eventDesc = getEventDesc(event);
  
    Long timelineDate = getTimelineDate(event);
  
    List<String> domains = getDomains(event);
  
    List<String> people = getPeople(event);
  
    return eventDao.saveEvent(eventName, eventDesc, timelineDate, domains, people);
  }

  /**
   * SERVICE: Delete an Existing Event by name (Cascades to relational tables)
   */
  @Transactional
  @Override
  public void deleteEvent(String event) {
      eventDao.deleteEvent(event);
    
  }
  /**
   * Update an Existing Event description AND/OR date by the Event's name
   */
  @Override
  public void updateEvent(String eventName, String eventDesc, String timelineDate) {
    eventDao.updateEvent(eventName, eventDesc, timelineDate);  
  }

  /**
   * @param event
   * @return
   */
  private Long getTimelineDate(Event event) {
    return eventDao.fetchTimelineDate(event.getTimelineDate())
        .orElseThrow(() -> new NoSuchElementException(
            "Domain Lord with Name=" + event.getTimelineDate() + " was not found"));
  }

  /**
   * @param event
   * @return
   */
  private String getEventDesc(Event event) {
    return eventDao.fetchEventDesc(event.getEventDesc())
        .orElseThrow(() -> new NoSuchElementException(
            "Domain Lord with Name=" + event.getEventDesc() + " was not found"));
  }

  /**
   * @param event
   * @return
   */
  private String getEventName(Event event) {
    return eventDao.fetchEventName(event.getEventName())
        .orElseThrow(() -> new NoSuchElementException(
            "Domain Lord with Name=" + event.getEventName() + " was not found"));
  }

  /**
   * @param event
   * @return
   */
  private List<String> getPeople(Event event) {
    return eventDao.fetchPeople(event.getPeople());
  }
  /**
   * 
   * @param event
   * @return
   */
  private List<String> getDomains(Event event) {
    return eventDao.fetchDomains(event.getDomains());
  }
}