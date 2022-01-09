package com.github.wenqiglantz.service.theaterservice.restcontroller;

import com.github.wenqiglantz.service.theaterservice.data.common.TheaterStatus;
import com.github.wenqiglantz.service.theaterservice.service.TheaterService;
import com.github.wenqiglantz.service.theaterservice.data.TheaterInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.github.wenqiglantz.service.theaterservice.restcontroller.ApiVersion.JSON;

@Slf4j
@RestController
@RequestMapping(value = "/theaters",
        produces = {JSON})
@Tag(name = "theater", description = "Operations pertaining to the Theater")
public class TheaterController {

    private final TheaterService theaterService;

    @Autowired
    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @Operation(summary = "Create theater")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a theater"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Authorization denied"),
            @ApiResponse(responseCode = "500", description = "Unexpected system exception"),
            @ApiResponse(responseCode = "502", description = "An error has occurred with an upstream service")
    })
    @PostMapping(consumes = {JSON})
    public ResponseEntity createTheater(@Valid @RequestBody TheaterInfo theaterInfo, UriComponentsBuilder uriBuilder) {
        TheaterInfo newTheaterInfo = theaterService.saveTheater(theaterInfo, TheaterStatus.PENDING);
        URI location = uriBuilder
                .path("/theaters/{theaterId}")
                .buildAndExpand(newTheaterInfo.getTheaterId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Retrieve the Theater details given the theater Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved a Theater"),
            @ApiResponse(responseCode = "401", description = "Authorization denied"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Unexpected system exception")
    })
    @GetMapping("/{theaterId}")
    public ResponseEntity<TheaterInfo> getTheater(@PathVariable String theaterId) {
        return ResponseEntity.ok(theaterService.getTheater(theaterId));
    }

    @Operation(summary = "Retrieve the Theater history given the theater Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Theater history"),
            @ApiResponse(responseCode = "401", description = "Authorization denied"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Unexpected system exception")
    })
    @GetMapping("/history/{theaterId}")
    public ResponseEntity<List<TheaterInfo>> getTheaterHistory(@PathVariable String theaterId) {
        return ResponseEntity.ok(theaterService.getTheaterHistory(theaterId));
    }
}
