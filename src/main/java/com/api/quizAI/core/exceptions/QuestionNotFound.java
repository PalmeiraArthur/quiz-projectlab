package com.api.quizAI.core.exceptions;

import java.util.UUID;

public class QuestionNotFound extends RuntimeException {
    public QuestionNotFound(UUID questionId) {
        super("Não foi possível encontrar a questão de id " + questionId);
    }
}
