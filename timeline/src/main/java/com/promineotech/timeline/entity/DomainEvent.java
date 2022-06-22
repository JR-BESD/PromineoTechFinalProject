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

public class DomainEvent {
  private int domain_id;
  private int event_id;
}
