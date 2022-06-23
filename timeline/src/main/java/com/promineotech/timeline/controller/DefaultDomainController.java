package com.promineotech.timeline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.timeline.entity.Domain;
import com.promineotech.timeline.service.DomainService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JamesR
 *
 */
@RestController
@Slf4j
public class DefaultDomainController implements DomainController {

  @Autowired
  private DomainService domainService;

  /**
   * CONTROLLER: Create new Domain
   */
  @Override
  public Domain createDomain(Domain domain) {
    log.debug("Domain={}", domain);
    return domainService.createDomain(domain);
  }
}
