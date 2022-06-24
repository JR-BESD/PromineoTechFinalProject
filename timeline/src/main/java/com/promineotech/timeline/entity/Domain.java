/**
 * 
 */
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
public class Domain {
  @JsonIgnore
  private Long domainId;
  @NotNull
  @Length(max = 30)
  private String domainName;
  @NotNull
  @Length(max = 1000)
  private String domainDesc;
  @NotNull
  @Length(max = 30)
  private String region;  
 }