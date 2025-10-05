package com.api.quizAI.core.exceptions;

import java.util.UUID;

public class AnswerNotFound extends RuntimeException
{
    public AnswerNotFound(UUID answerId) {
        super("Não foi possível achar alternativa de id {}" + answerId);
    }
}
