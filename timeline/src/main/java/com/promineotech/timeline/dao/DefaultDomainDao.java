/**
 * 
 */
package com.promineotech.timeline.dao;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.promineotech.timeline.entity.Domain;

/**
 * @author JamesR
 *
 */
@Component
public class DefaultDomainDao implements DomainDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public Domain saveDomain(String domainName, String domainDesc, String region) {
    SqlParams params = generateInsertSql(domainName, domainDesc, region);

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(params.sql, params.source, keyHolder);

    Long domainId = keyHolder.getKey().longValue();
    
    //@formatter:off
    return Domain.builder()
        .domainId(domainId)
        .domainName(domainName)
        .domainDesc(domainDesc)
        .region(region)     
        .build();
    //@formatter:on
  }

  /**
   * @param domainName
   * @param domainDesc
   * @param region
   * @param domainLord
   * @return
   */
  private SqlParams generateInsertSql(String domainName, String domainDesc, String region) {
  //@formatter:off
    String sql = ""
        + "INSERT INTO domains ("
        + "domain_name, domain_desc, region"
        + ") VALUES("
        + ":domain_name, :domain_desc, :region"
        + ")";
    //@formatter:off
    
    SqlParams params = new SqlParams();
    
    params.sql = sql;
    params.source.addValue("domain_name", domainName);
    params.source.addValue("domain_desc", domainDesc);
    params.source.addValue("region", region);   
    
    
    return params;
  }
  
  class SqlParams {
    String sql;
    MapSqlParameterSource source = new MapSqlParameterSource();
  }

  @Override
  public Optional<String> fetchDomainName(String domainName) {
    return Optional.of(domainName);
  }

  @Override
  public Optional<String> fetchDomainDesc(String domainDesc) {
    return Optional.of(domainDesc);
  }

  @Override
  public Optional<String> fetchRegion(String region) {
    return Optional.of(region);
  }
 
}
