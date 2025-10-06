package com.api.quizAI.core.exceptions;


import java.util.UUID;

public class QuizNotFound extends RuntimeException
{
    public QuizNotFound(UUID id) {
        super("Não foi possível encontrar o quiz de id " + id);
    }
}
