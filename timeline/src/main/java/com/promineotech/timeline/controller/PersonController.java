package com.promineotech.timeline.controller;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.promineotech.timeline.entity.Domain;
import com.promineotech.timeline.entity.Person;
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
@RequestMapping("/people")
@OpenAPIDefinition(info = @Info(title = "Person Post Service"),
    servers = {@Server(url = "http://localhost:8080", description = "Local Server.")})

public interface PersonController {

  //@formatter:off
  @Operation(
      summary = "Create a new Person",
      description = "Returns created Person",
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "The created Person is returned",
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
              description = "A person was not found with the input criteria",
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500",
              description = "An unplanned error occured",
              content = @Content(
                  mediaType = "application/json"))
      },
      parameters = {
         @Parameter(name = "person",             
             required = false, 
             description = "The Person as JSON")
         }
      )
 
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  Person createPerson(@Valid @RequestBody Person person);      
  //@formatter:on
}
