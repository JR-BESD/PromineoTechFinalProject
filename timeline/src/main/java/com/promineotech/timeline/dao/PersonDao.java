package com.promineotech.timeline.dao;

import java.util.Optional;
import com.promineotech.timeline.entity.Person;

/**
 * @author JamesR
 *
 */
public interface PersonDao {

  /**
   * @param name
   * @param race
   * @param description
   * @param domain
   * @return
   */
  Person savePerson(String name, String race, String description, String domain);

  /**
   * @param name
   * @return
   */
  Optional<String> fetchName(String name);

  /**
   * @param race
   * @return
   */
  Optional<String> fetchRace(String race);

  /**
   * @param description
   * @return
   */
  Optional<String> fetchDescription(String description);

  /**
   * @param domain
   * @return
   */
  Optional<String> fetchDomain(String domain);
}