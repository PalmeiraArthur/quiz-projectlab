package com.api.quizAI.web.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RoomRequestDTO(
        @NotNull
        boolean isPublic,

        int maxNumberOfPlayersInRoom,

        @NotNull
        UUID ownerId
) {
}
