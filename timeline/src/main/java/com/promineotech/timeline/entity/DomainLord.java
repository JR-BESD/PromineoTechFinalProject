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

public class DomainLord {
  private int personId;
  private int domainId;
  private String name;
}