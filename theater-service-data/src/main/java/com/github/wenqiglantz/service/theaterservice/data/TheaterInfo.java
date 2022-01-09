package com.github.wenqiglantz.service.theaterservice.data;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"theaterId", "customSiteId", "customSiteCode", "creatorSubjectId", "creatorIssuerId", "status"})
public class TheaterInfo {

    @Schema(required = false, description = "The ID for the theater. Should be a Unique ID with max 36 Characters. If not provided, the system will assign one.")
    @Size(max = 36)
    private String theaterId;

    @Schema(required = false, description = "The ID for the custom site. Should be a Unique ID with max 36 Characters. If not provided, the system will assign one.")
    @Size(max = 36)
    private String customSiteId;

    @Schema(required = true, description = "The custom site code. The length cannot exceed 16.")
    @NotBlank @Size(max = 16)
    private String customSiteCode;

    @Schema(required = true, description = "The creator subject id. The length cannot exceed 255.")
    @NotBlank @Size(max = 255)
    private String creatorSubjectId;

    @Schema(required = true, description = "The creator issuer id. The length cannot exceed 255.")
    @NotBlank @Size(max = 255)
    private String creatorIssuerId;

    @Schema(required = false, description = "The status of the theater. The length cannot exceed 32.")
    @Size(max = 32)
    private String status;
}
