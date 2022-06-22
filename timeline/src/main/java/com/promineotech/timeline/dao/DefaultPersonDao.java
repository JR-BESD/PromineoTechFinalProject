package com.promineotech.timeline.dao;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.promineotech.timeline.entity.Person;

/**
 * @author JamesR
 *
 */
@Component
public class DefaultPersonDao implements PersonDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public Person savePerson(String name, String race, String description, String domain) {
    SqlParams params = generateInsertSql(name, race, description, domain);

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(params.sql, params.source, keyHolder);

    Long personId = keyHolder.getKey().longValue();
  

  //@formatter:off
    return Person.builder()
        .personId(personId)
        .name(name)
        .race(race)
        .description(description) 
        .domain(domain)
        .build();
    //@formatter:on
  }

  /**
   * @param name
   * @param race
   * @param description
   * @param domainId
   * @return
   */
  private SqlParams generateInsertSql(String name, String race, String description, String domain) {
    //@formatter:off
    String sql = ""
        + "INSERT INTO people ("
        + "name, race, description, domain_id"
        + ") VALUES ("
        + ":name, :race, :description, ("
        + "SELECT domain_id "
        + "FROM domains "
        + "WHERE domain_name = :domain_name))";
    //@formatter:on

    SqlParams params = new SqlParams();

    params.sql = sql;
    params.source.addValue("name", name);
    params.source.addValue("race", race);
    params.source.addValue("description", description);
    params.source.addValue("domain_name", domain);

    return params;
  }

  @Override
  public Optional<String> fetchName(String name) {
    return Optional.of(name);
  }

  @Override
  public Optional<String> fetchRace(String race) {
    return Optional.of(race);
  }

  @Override
  public Optional<String> fetchDescription(String description) {
    return Optional.of(description);
  }

  @Override
  public Optional<String> fetchDomain(String domain) {
    return Optional.of(domain);
  }

  class SqlParams {
    String sql;
    MapSqlParameterSource source = new MapSqlParameterSource();
  }

}
