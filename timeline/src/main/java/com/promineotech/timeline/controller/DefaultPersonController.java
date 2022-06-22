package com.promineotech.timeline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.timeline.entity.Person;
import com.promineotech.timeline.service.PersonService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JamesR
 *
 */
@RestController
@Slf4j
public class DefaultPersonController implements PersonController {
  
  @Autowired
  private PersonService personService;

  @Override
  public Person createPerson(Person person) {
    log.debug("Person={}", person);
    return personService.createPerson(person);
  }

}
