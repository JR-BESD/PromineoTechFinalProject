package com.promineotech.timeline.service;

import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.promineotech.timeline.dao.PersonDao;
import com.promineotech.timeline.entity.Person;

/**
 * @author JamesR
 *
 */
@Service
public class DefaultPersonService implements PersonService {
  
  @Autowired
  private PersonDao personDao;
  
  /**
   * SERVICE: Create new Person
   */
  @Transactional
  @Override
  public Person createPerson(Person person) {
    String name = getName(person);
    String race = getRace(person);
    String description = getDescription(person);
    String domain = getDomain(person);
    
    return personDao.savePerson(name, race, description, domain);
  }

  /**
   * @param person
   * @return
   */
  private String getName(Person person) {
    return personDao.fetchName(person.getName())
        .orElseThrow(() -> new NoSuchElementException(
            "Domain Lord with Name=" + person.getName() + " was not found"));
  }

  /**
   * @param person
   * @return
   */
  private String getRace(Person person) {
    return personDao.fetchRace(person.getRace())
        .orElseThrow(() -> new NoSuchElementException(
            "Domain Lord with Name=" + person.getRace() + " was not found"));
  }

  /**
   * @param person
   * @return
   */
  private String getDescription(Person person) {
    return personDao.fetchDescription(person.getDescription())
        .orElseThrow(() -> new NoSuchElementException(
            "Domain Lord with Name=" + person.getDescription() + " was not found"));
  }

  /**
   * @param person
   * @return
   */
  private String getDomain(Person person) {
    return personDao.fetchDomain(person.getDomain())
        .orElseThrow(() -> new NoSuchElementException(
            "Domain Lord with Name=" + person.getDomain() + " was not found"));
  }
}
