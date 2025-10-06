package com.api.quizAI.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AnswerRequestDTO(
        @NotNull
        UUID userId,

        @NotNull
        UUID answerId,

        @Schema(example = "2025-10-05T11:07:00-03:00")
        @NotNull(message = "Deve ser indicado data e hora como string no formato ISO-8601")
        OffsetDateTime sentAt
) {
}
