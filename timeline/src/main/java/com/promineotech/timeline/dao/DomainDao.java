package com.promineotech.timeline.dao;

import java.util.Optional;
import com.promineotech.timeline.entity.Domain;

/**
 * @author JamesR
 *
 */
public interface DomainDao {
  
  
  /**
   * @param domainName
   * @param domainDesc
   * @param region
   * @return
   */
  Domain saveDomain(String domainName, String domainDesc, String region);

  /**
   * @param domainName
   * @return
   */
  Optional<String> fetchDomainName(String domainName);

  /**
   * @param domainDesc
   * @return
   */
  Optional<String> fetchDomainDesc(String domainDesc);

  /**
   * @param region
   * @return
   */
  Optional<String> fetchRegion(String region);
}

