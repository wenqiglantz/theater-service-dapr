package com.github.wenqiglantz.service.theaterservice.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wenqiglantz.service.theaterservice.data.event.TheaterCreatedEvent;
import com.github.wenqiglantz.service.theaterservice.service.TheaterService;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "subscriber", description = "Operations pertaining to event consumption")
public class SubscriberController {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private final TheaterService theaterService;

  @Autowired
  public SubscriberController(TheaterService theaterService) {
    this.theaterService = theaterService;
  }

  @Operation(summary = "Consume TheaterCreatedEvent")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully consumed TheaterCreatedEvent"),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "500", description = "Unexpected system exception"),
          @ApiResponse(responseCode = "502", description = "An error has occurred with an upstream service")
  })
  @Topic(name = "dii.integration.customerservice.theaterv1", pubsubName = "dii-integration")
  @PostMapping(path = "/dii.integration.customerservice.theaterv1")
  public void consumeEvent(@RequestBody(required = false) CloudEvent event) {
      try {
        log.info("Subscriber received: " + OBJECT_MAPPER.writeValueAsString(event));
        TheaterCreatedEvent theaterCreatedEvent = OBJECT_MAPPER.convertValue(event.getData(), TheaterCreatedEvent.class);

        theaterService.consumeTheaterCreatedEvent(theaterCreatedEvent);

      } catch (Exception e) {
        throw new RuntimeException(e);
      }
  }

  /*
  A Mono<T> is a specialized Publisher<T> that emits at most one item and then (optionally) terminates with an onComplete signal or an onError signal.
  Mono is just a function wrapped inside the class which iterates over the data source and pushes the values to the consumer and returns Subscription so that consumer can say “i dont need more data”.
  Publisher of type Mono<Void> can never emit a value, only a completion signal.
  Using Mono<T> here, non blocking, reactive, better performance
   */
  /*
  public Mono<Void> consumeEvent(@RequestBody(required = false) CloudEvent event) {
    return Mono.fromRunnable(() -> {
      try {
        log.info("Subscriber received: " + OBJECT_MAPPER.writeValueAsString(event));
        TheaterCreatedEvent theaterCreatedEvent = OBJECT_MAPPER.convertValue(event.getData(), TheaterCreatedEvent.class);

        theaterService.consumeTheaterCreatedEvent(theaterCreatedEvent);

      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }
   */

}
