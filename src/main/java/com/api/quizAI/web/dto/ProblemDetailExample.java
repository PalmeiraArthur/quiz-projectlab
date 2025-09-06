package com.api.quizAI.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.net.URI;

public record ProblemDetailExample(
        @Schema(example = "https://AIQuiz.com/pt-BR/docs/Web/HTTP/Status/404")
        String type,

        @Schema(example = "Quiz não encontrado")
        String title,

        @Schema(example = "404")
        int status,

        @Schema(example = "Quiz com id 103 não encontrado")
        String detail,

        @Schema(example = "/quiz/103")
        URI instance
){}
