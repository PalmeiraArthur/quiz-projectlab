package com.api.quizAI.web.dto;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AnswerRequestDTO(
        @NotNull
        UUID userId,

        @NotNull
        UUID answerId,

        @NotNull
        OffsetDateTime sentAt
) {
}
