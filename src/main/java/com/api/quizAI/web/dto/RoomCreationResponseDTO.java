package com.api.quizAI.web.dto;

import com.api.quizAI.core.domain.User;

import java.util.UUID;

public record RoomCreationResponseDTO(
        UUID id,

        String roomCode,

        Boolean isPublic,

        Integer maxNumberOfPlayers,

        User owner
) {
}
