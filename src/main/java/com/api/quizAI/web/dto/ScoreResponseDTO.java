package com.api.quizAI.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ScoreResponseDTO(
        @Schema(example = "20")
        int totalUserPoints
) {
}
