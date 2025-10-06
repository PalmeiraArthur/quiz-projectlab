package com.api.quizAI.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RoomRequestDTO(
        @NotNull
        boolean isPublic,

        @Schema(example = "8")
        int maxNumberOfPlayersInRoom,

        @NotNull
        UUID ownerId
) {
}
