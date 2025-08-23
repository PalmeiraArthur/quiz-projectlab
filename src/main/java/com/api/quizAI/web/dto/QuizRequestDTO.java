package com.api.quizAI.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record QuizRequestDTO(
        @NotBlank(message = "O tópico para as questões não deve estar em branco")
        String topic,

        @Positive(message = "O número de perguntas deve ser positivo")
        @Max(value = 15, message = "O número máximo de perguntas deve ser 15")
        Integer numberOfQuestions,

        @Positive(message = "O número de alternativas deve ser positivo")
        @Max(value = 6, message = "O número máximo de alternativas deve ser 6")
        Integer numberOfAnswers
) {}
