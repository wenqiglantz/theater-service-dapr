package com.github.wenqiglantz.service.theaterservice.data.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"theaterId"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class TheaterCreatedEvent {

    @Schema(required = true, description = "The ID for the theater which has just been created from the publisher.")
    @Size(max = 36)
    private String theaterId;
}
