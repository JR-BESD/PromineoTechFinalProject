package com.promineotech.timeline.service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.promineotech.timeline.dao.EventDao;
import com.promineotech.timeline.entity.Event;
import com.promineotech.timeline.entity.EventRelationship;
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
  
  @Transactional
  @Override
  public void deleteEvent(String event) {
      eventDao.deleteEvent(event);
    
  }
  @Transactional
  @Override
  public Event createEvent(EventRelationship eventRelationship) {
    String eventName = getEventName(eventRelationship);

    String eventDesc = getEventDesc(eventRelationship);

    String timelineDate = getTimelineDate(eventRelationship);

    List<String> domains = getDomains(eventRelationship);

    List<String> people = getPeople(eventRelationship);

    return eventDao.saveEvent(eventName, eventDesc, timelineDate, domains, people);
  }

  /**
   * @param eventRelationship
   * @return
   */
  private String getTimelineDate(EventRelationship eventRelationship) {
    return eventDao.fetchTimelineDate(eventRelationship.getTimelineDate())
        .orElseThrow(() -> new NoSuchElementException(
            "Domain Lord with Name=" + eventRelationship.getTimelineDate() + " was not found"));
  }

  /**
   * @param eventRelationship
   * @return
   */
  private String getEventDesc(EventRelationship eventRelationship) {
    return eventDao.fetchEventDesc(eventRelationship.getEventDesc())
        .orElseThrow(() -> new NoSuchElementException(
            "Domain Lord with Name=" + eventRelationship.getEventDesc() + " was not found"));
  }

  /**
   * @param eventRelationship
   * @return
   */
  private String getEventName(EventRelationship eventRelationship) {
    return eventDao.fetchEventName(eventRelationship.getEventName())
        .orElseThrow(() -> new NoSuchElementException(
            "Domain Lord with Name=" + eventRelationship.getEventName() + " was not found"));
  }

  /**
   * @param eventRelationship
   * @return
   */
  private List<String> getPeople(EventRelationship eventRelationship) {
    return eventDao.fetchPeople(eventRelationship.getPeople());
  }
  /**
   * 
   * @param eventRelationship
   * @return
   */
  private List<String> getDomains(EventRelationship eventRelationship) {
    return eventDao.fetchDomains(eventRelationship.getDomains());
  }

  /**
   * @param eventName
   * @param eventDesc
   * @param timelineDate 
   */
  @Override
  public void updateEvent(String eventName, String eventDesc, String timelineDate) {
    eventDao.updateEvent(eventName, eventDesc, timelineDate);  
  }

}
