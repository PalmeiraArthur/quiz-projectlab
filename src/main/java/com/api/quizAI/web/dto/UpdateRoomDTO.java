package com.api.quizAI.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateRoomDTO(
        @Schema(nullable = true)
        Boolean isPublic,

        @Schema(example = "8", nullable = true)
        Integer maxNumberOfPlayers,

        @Schema(nullable = true)
        UUID quizId,

        @NotNull
        UUID ownerId
) {
}
