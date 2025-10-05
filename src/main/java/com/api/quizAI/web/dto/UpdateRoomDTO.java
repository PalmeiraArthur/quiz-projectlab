package com.api.quizAI.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateRoomDTO(
        Boolean isPublic,

        Integer maxNumberOfPlayers,

        UUID quizId,

        @NotNull
        UUID ownerId
) {
}
