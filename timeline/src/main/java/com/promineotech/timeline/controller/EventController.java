package com.promineotech.timeline.controller;

import java.util.List;
import javax.validation.Valid;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.promineotech.timeline.entity.Event;
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
@RequestMapping("/events")
@OpenAPIDefinition(info = @Info(title = "Timeline Event Service"),
    servers = {@Server(url = "http://localhost:8080", description = "Local Server.")})

public interface EventController {
 
  //@formatter:off
  @Operation(
      summary = "Returns a list of Events",
      description = "Returns a list of Events given an optional domain and/or date",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "A list of Events returned",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Event.class))),
          @ApiResponse(
              responseCode = "400",
              description = "The request parameters are invalid",
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404",
              description = "No Events were found with the input criteria",
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500",
              description = "An unplanned error occured",
              content = @Content(
                  mediaType = "application/json"))
      },
      parameters = {
          @Parameter(name = "domain", allowEmptyValue = false, required = false, description = "A Domain name (i.e., 'Barovia'"),
          @Parameter(name = "date", allowEmptyValue = false, required = false, description = "A date on the Barovian Calender"),
          @Parameter(name = "person", allowEmptyValue = false, required = false, description = "A person's name")
          
      })
 
  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  List<Event> fetchEvents(
      @Length(max = 30)    
      @RequestParam(required = false) 
          String domain, 
      @RequestParam(required = false) 
          Long date,
      @RequestParam(required = false) 
          String person);
  //@formatter:on
  
//@formatter:off
  @Operation(
      summary = "Deletes an Events",
      description = "Returns a list of Events given an optional domain and/or date",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Event deleted successfully",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Event.class))),
          @ApiResponse(
              responseCode = "400",
              description = "The request parameters are invalid",
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404",
              description = "No Events were found with the input criteria",
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500",
              description = "An unplanned error occured",
              content = @Content(
                  mediaType = "application/json"))
      },
      parameters = {
          @Parameter(name = "event", allowEmptyValue = false, required = false, description = "A Domain name (i.e., 'Barovia'")
         
          
      })
  
  @DeleteMapping
  @ResponseStatus(code = HttpStatus.OK)
  void deleteEvent(@Length(max = 30)    
  @RequestParam(required = true) 
  String event);
  
  @Operation(
      summary = "Create a new Event",
      description = "Returns created Event",
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "The created Event is returned",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Event.class))),
          @ApiResponse(
              responseCode = "400",
              description = "The request parameters are invalid",
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404",
              description = "An event component was not found with the input criteria",
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500",
              description = "An unplanned error occured",
              content = @Content(
                  mediaType = "application/json"))
      },
      parameters = {
         @Parameter(name = "eventRelationship",             
             required = false, 
             description = "The Event as JSON")
         }
      )
  //@formatter:on
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  Event createEvent(@Valid @RequestBody Event event);

  //@formatter:off
  @Operation(
      summary = "Update an existing Event",
      description = "Returns created Event",
      responses = {
          @ApiResponse(
              responseCode = "20",
              description = "The event has been updated",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Event.class))),
          @ApiResponse(
              responseCode = "400",
              description = "The request parameters are invalid",
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404",
              description = "An event name was not found with the input criteria",
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500",
              description = "An unplanned error occured",
              content = @Content(
                  mediaType = "application/json"))
      },
      parameters = {
         @Parameter(name = "eventName",             
             required = true, 
             description = "An Event name to be updated"),
         @Parameter(name = "eventDesc",             
         required = false, 
         description = "The updated description"),
         @Parameter(name = "timelineDate",             
         required = false, 
         description = "The updated date on the Barovian calendar")
         }
      )
  //formatter:on
  @PutMapping
  @ResponseStatus(code = HttpStatus.OK)
  //@formatter:off
  void updateEvent(
      @RequestParam(required = true) String eventName,
      @RequestParam(required = false)String eventDesc,
      @RequestParam(required = false)String timelineDate);
  //@formatter:on
}