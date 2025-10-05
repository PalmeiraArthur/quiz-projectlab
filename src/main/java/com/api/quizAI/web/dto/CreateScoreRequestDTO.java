package com.api.quizAI.web.dto;


import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateScoreRequestDTO(
        @NotNull
        UUID userId,

        @NotNull
        UUID roomId
) {
}
