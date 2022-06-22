package com.promineotech.timeline.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.promineotech.timeline.entity.Event;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JamesR
 *
 */
@Component
@Slf4j
public class DefaultEventDao implements EventDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  /**
   * DAO: Retrieve a list of Events from Person AND/OR Domain AND/OR Date
   */
  @Override
  public List<Event> fetchEvents(String domain, Long date, String person) {
    log.debug("DAO: domain={}, date={}, person = {}", domain, date, person);
    String sql = "";
    if (domain != null && date != null && person != null) {
      //@formatter:off
      sql = "SELECT * "
          + "FROM events e "
          + "JOIN domain_events de ON e.event_id = de.event_id "
          + "JOIN event_people ep ON e.event_id = ep.event_id "
          + "JOIN domains d ON de.domain_id = d.domain_id "
          + "JOIN people p ON p.person_id = ep.person_id "
          + "WHERE domain_name = :domain_name AND timeline_date = :timeline_date AND name = :name";
      //@formatter:on
    } else if (domain != null && date != null && person == null) {
    //@formatter:off
    sql = ""
        + "SELECT e.event_id, domain_name, event_name, event_desc, timeline_date FROM domain_events de "
        + "JOIN domains d ON d.domain_id = de.domain_id "
        + "JOIN events e ON e.event_id = de.event_id "
        + "WHERE domain_name = :domain_name AND timeline_date = :timeline_date;";
    //@formatter:on
    } else if (domain != null && date == null && person == null) {
      //@formatter:off
        sql = ""
            + "SELECT * FROM events e "
            + "JOIN domain_events de ON de.event_id = e.event_id "
            + "JOIN domains d ON de.domain_id = d.domain_id "
            + "WHERE domain_name = :domain_name";
        //@formatter:on

    } else if (domain == null && date != null && person == null) {
      //@formatter:off
      sql = ""
          + "SELECT * "
          + "FROM events "
          + "WHERE timeline_date = :timeline_date";
      //@formatter:on

    } else if (domain == null && date == null && person != null) {
      //@formatter:off
      sql = ""
          + "SELECT * "
          + "FROM events e "
          + "JOIN event_people ep ON e.event_id = ep.event_id "
          + "JOIN people p ON p.person_id = ep.person_id "
          + "WHERE name = :name";
      //@formatter:on
    } else if (domain == null && date != null && person != null) {
      //@formatter:off
      sql = ""
          + "SELECT * "
          + "FROM events e "
          + "JOIN event_people ep ON e.event_id = ep.event_id "
          + "JOIN people p ON p.person_id = ep.person_id "
          + "WHERE name = :name AND timeline_date = :timeline_date ";
      //@formatter:on
    } else if (domain != null && date == null && person != null) {
      //@formatter:off
      sql = ""
          + "SELECT * "
          + "FROM events e "
          + "JOIN event_people ep ON e.event_id = ep.event_id "
          + "JOIN people p ON p.person_id = ep.person_id "
          + "JOIN domain_events de ON de.event_id = e.event_id "
          + "JOIN domains d ON d.domain_id = de.domain_id "
          + "WHERE name = :name AND domain_name = :domain_name";
      //@formatter:on
    }

    Map<String, Object> params = new HashMap<>();
    params.put("domain_name", domain);
    params.put("timeline_date", date);
    params.put("name", person);

    return jdbcTemplate.query(sql, params, new RowMapper<>() {

      @Override
      public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        //@formatter:off
        return Event.builder()
            .eventId(rs.getLong("event_id"))
            .eventName(rs.getString("event_name"))
            .eventDesc(rs.getString("event_desc"))
            .timelineDate(rs.getLong("timeline_date"))
            .build();
        //@formatter:on
      }
    });
  }

  @Override
  public Optional<String> fetchEventName(String eventName) {
    return Optional.of(eventName);
  }

  class EventNameResultSetExtractor implements ResultSetExtractor<String> {

    @Override
    public String extractData(ResultSet rs) throws SQLException, DataAccessException {
      rs.next();
      return rs.getString("event_name");
    }
  }

  @Override
  public Optional<String> fetchEventDesc(String eventDesc) {
    return Optional.of(eventDesc);
  }

  class EventDescResultSetExtractor implements ResultSetExtractor<String> {

    @Override
    public String extractData(ResultSet rs) throws SQLException, DataAccessException {
      rs.next();
      return rs.getString("event_desc");
    }
  }

  @Override
  public Optional<Long> fetchTimelineDate(Long timelineDate) {
    return Optional.of(timelineDate);
  }

  class TimelineDateResultSetExtractor implements ResultSetExtractor<String> {

    @Override
    public String extractData(ResultSet rs) throws SQLException, DataAccessException {
      rs.next();
      return rs.getString("timeline_date");
    }
  }

  @Override
  public List<String> fetchDomains(List<String> domainNames) {
    if (domainNames.isEmpty()) {
      return new LinkedList<>();
    }
    return domainNames;
  }

  @Override
  public List<String> fetchPeople(List<String> people) {
    if (people.isEmpty()) {
      return new LinkedList<>();
    }
    return people;
  }

  class EventResultSetExtractor implements ResultSetExtractor<Event> {

    @Override
    public Event extractData(ResultSet rs) throws SQLException, DataAccessException {
      rs.next();

      //@formatter:off
      return Event.builder()
          .eventId(rs.getLong("event_id"))
          .eventName(rs.getString("event_name"))
          .eventDesc(rs.getString("event_desc")) 
          .timelineDate(rs.getLong("timeline_date"))
          .build();
      //@formatter:on
    }
  }

  /**
   * DAO: Insert a New Event with domain and person relationships
   */
  @Override
  public Event saveEvent(String eventName, String eventDesc, Long timelineDate,
      List<String> domains, List<String> people) {
    SqlParams params = generateInsertSql(eventName, eventDesc, timelineDate);

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(params.sql, params.source, keyHolder);

    Long eventId = keyHolder.getKey().longValue();

    saveDomains(domains, eventId);
    savePeople(people, eventId);

    //@formatter:off
    return Event.builder()
        .eventId(eventId)
        .eventName(eventName)
        .eventDesc(eventDesc)
        .timelineDate(Long.valueOf(timelineDate))
        .domains(domains)
        .people(people)
        .build();
    //@formatter:on
  }

  /**
   * 
   * @param eventName
   * @param eventDesc
   * @param timelineDate
   * @param domainLord
   * @return
   */
  private SqlParams generateInsertSql(String eventName, String eventDesc, Long timelineDate) {
    //@formatter:off
    String sql = ""
        + "INSERT INTO events ("
        + "event_name, event_desc, timeline_date"
        + ") VALUES("
        + ":event_name, :event_desc, :timeline_date"
        + ")";
    //@formatter:off
    
    SqlParams params = new SqlParams();
    
    params.sql = sql;
    params.source.addValue("event_name", eventName);
    params.source.addValue("event_desc", eventDesc);
    params.source.addValue("timeline_date", timelineDate);   
    
    return params;
  }

  /**
   * @param domains
   * @param eventId
   */
  private void saveDomains(List<String> domains, Long eventId) {
    for (String domain : domains) {
      SqlParams params = generateInsertDomainSql(domain, eventId);
      jdbcTemplate.update(params.sql, params.source);
    }
  }

  /**
   * @param domain
   * @param eventId
   * @return
   */
  private SqlParams generateInsertDomainSql(String domain, Long eventId) {
    SqlParams params = new SqlParams();
    //@formatter:off
    params.sql = "" 
        + "INSERT INTO domain_events (" 
        + "domain_id, event_id" + ") VALUES("
        + "(SELECT domain_id "
        + "FROM domains "
        + "WHERE domain_name = :domain_name), :event_id" + ")";
    //@formatter:on
    params.source.addValue("domain_name", domain);
    params.source.addValue("event_id", eventId);
    return params;
  }

  /**
   * @param people
   * @param eventId
   */
  private void savePeople(List<String> people, Long eventId) {
    for (String person : people) {
      SqlParams params = generateInsertPersonSql(person, eventId);
      jdbcTemplate.update(params.sql, params.source);
    }
  }

  /**
   * @param person
   * @param eventId
   * @return
   */
  private SqlParams generateInsertPersonSql(String person, Long eventId) {
    SqlParams params = new SqlParams();
  //@formatter:off
    params.sql = "" 
        + "INSERT INTO event_people (" 
        + "person_id, event_id" 
        + ") VALUES("
        + "(SELECT person_id"
        + "FROM people "
        + "WHERE name = :name), :event_id" 
        + ")";
  //@formatter:on
    params.source.addValue("name", person);
    params.source.addValue("event_id", eventId);
    return params;
  }

  /**
   * DAO: Update an Existing Event description AND/OR date by the Event's name
   */
  @Override
  public void updateEvent(String eventName, String eventDesc, String timelineDate) {
    SqlParams params = generateUpdateSql(eventName, eventDesc, timelineDate);

    jdbcTemplate.update(params.sql, params.source);
  }

  /**
   * @param eventName
   * @return
   */
  private SqlParams generateUpdateSql(String eventName, String eventDesc, String timelineDate) {
    SqlParams params = new SqlParams();
    //@formatter:off
    if (eventDesc != null && timelineDate != null) {
    params.sql = ""
        + "UPDATE events "
        + "SET event_desc = :event_desc, "
        + "timeline_date = :timeline_date "
        + "WHERE event_name = :event_name";
    //@formatter:on
    } else if (eventDesc == null && timelineDate != null) {
      params.sql = "" + "UPDATE events " + "SET " + "timeline_date = :timeline_date "
          + "WHERE event_name = :event_name";
      //@formatter:on
    } else if (eventDesc != null && timelineDate == null) {
      params.sql = "" + "UPDATE events " + "SET event_desc = :event_desc "
          + "WHERE event_name = :event_name";
      //@formatter:on
    }
    params.source.addValue("event_desc", eventDesc);
    params.source.addValue("timeline_date", timelineDate);
    params.source.addValue("event_name", eventName);
    return params;
  }

  /**
   * DAO: Delete an Existing Event by name (Cascades to relational tables)
   */
  @Override
  public void deleteEvent(String event) {
    //@formatter:off
    String sql = ""
        + "DELETE FROM events "
        + "WHERE event_name = :event_name";        
    //@formatter:on    
    Map<String, Object> params = new HashMap<>();
    params.put("event_name", event);


    jdbcTemplate.update(sql, params);
  }

  class SqlParams {
    String sql;
    MapSqlParameterSource source = new MapSqlParameterSource();
  }
}
