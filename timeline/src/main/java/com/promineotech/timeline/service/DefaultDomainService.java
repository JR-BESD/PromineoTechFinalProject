package com.promineotech.timeline.service;

import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.promineotech.timeline.dao.DomainDao;
import com.promineotech.timeline.entity.Domain;

/**
 * @author JamesR
 *
 */
@Service
public class DefaultDomainService implements DomainService {

  @Autowired
  private DomainDao domainDao;


  @Transactional
  @Override
  public Domain createDomain(Domain domain) {
    String domainName = getDomainName(domain);
    String domainDesc = getDomainDesc(domain);
    String region = getRegion(domain);

    return domainDao.saveDomain(domainName, domainDesc, region);
  }

  /**
   * @param domain
   * @return
   */
  private String getDomainName(Domain domain) {
    return domainDao.fetchDomainName(domain.getDomainName())
        .orElseThrow(() -> new NoSuchElementException(
            "Domain Lord with Name=" + domain.getDomainName() + " was not found"));
  }

  /**
   * @param domain
   * @return
   */
  private String getDomainDesc(Domain domain) {
    return domainDao.fetchDomainDesc(domain.getDomainDesc())
        .orElseThrow(() -> new NoSuchElementException(
            "Domain Lord with Name=" + domain.getDomainName() + " was not found"));
  }

  /**
   * @param domain
   * @return
   */
  private String getRegion(Domain domain) {
    return domainDao.fetchRegion(domain.getRegion())
        .orElseThrow(() -> new NoSuchElementException(
            "Domain Lord with Name=" + domain.getDomainName() + " was not found"));
  }
}
