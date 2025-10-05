package com.api.quizAI.core.exceptions;

import java.util.UUID;

public class ScoreNotFound extends RuntimeException
{
    public ScoreNotFound(UUID scoreId) {
        super("Não foi possível encontrar score de id" + scoreId);
    }
}
