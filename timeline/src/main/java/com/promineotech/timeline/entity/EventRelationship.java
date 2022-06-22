package com.promineotech.timeline.entity;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
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
public class EventRelationship {

  @NotNull
  @Length(max = 30)
  private String eventName;
  @NotNull
  @Length(max = 1000)
  private String eventDesc;
  @NotNull
  @Length(max = 15)
  private String timelineDate; 
  private List<@NotNull @Length(max = 30) String> domains;
  private List<@NotNull @Length(max = 30) String> people;
}
