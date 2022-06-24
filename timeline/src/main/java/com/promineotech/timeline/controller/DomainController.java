package com.promineotech.timeline.controller;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.promineotech.timeline.entity.Domain;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * @author JamesR
 *
 */
@Validated
@RequestMapping("/domains")
@OpenAPIDefinition(info = @Info(title = "Domain Post Service"),
    servers = {@Server(url = "http://localhost:8080", description = "Local Server.")})

public interface DomainController {

  //@formatter:off
  @Operation(
      summary = "Create a new Domain",
      description = "Returns created Domain",
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "The created Domain is returned",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Domain.class))),
          @ApiResponse(
              responseCode = "400",
              description = "The request parameters are invalid",
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404",
              description = "A domain was not found with the input criteria",
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500",
              description = "An unplanned error occured",
              content = @Content(
                  mediaType = "application/json"))
      },
      parameters = {
         @Parameter(name = "domain",             
             required = false, 
             description = "The Domain as JSON")
         }
      )
 
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  Domain createDomain(@Valid @RequestBody Domain domain);
      
  //@formatter:on
}