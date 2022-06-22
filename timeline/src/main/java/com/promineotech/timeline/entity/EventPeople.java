package com.promineotech.timeline.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JamesR
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class EventPeople {
  private int person_id;
  private int event_id;  
}
