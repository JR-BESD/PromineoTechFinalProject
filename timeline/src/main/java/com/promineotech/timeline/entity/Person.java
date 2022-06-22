package com.promineotech.timeline.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Person {
  @JsonIgnore
  private Long personId;
  @NotNull
  @Length(max = 30)
  private String name;
  @NotNull
  @Length(max = 30)
  private String race;
  @NotNull
  @Length(max = 1000)
  private String description;
  @NotNull
  @Length(max = 30)
  private String domain;
  
  

}
