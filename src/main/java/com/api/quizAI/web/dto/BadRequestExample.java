package com.api.quizAI.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.net.URI;
import java.util.List;

public record BadRequestExample(
        @Schema(example = "https://AIQuiz.com/pt-BR/docs/Web/HTTP/Status/400")
        String type,

        @Schema(example = "Parâmetro inválido")
        String title,

        @Schema(example = "400")
        int status,

        @Schema(example = "Parâmetros com valores inválidos")
        String detail,

        @Schema(example = "/quiz")
        URI instance,

        List<MessageError> errors
) {
}

record MessageError(
        @Schema(example = "O número de perguntas deve ser positivo")
        String message
){}