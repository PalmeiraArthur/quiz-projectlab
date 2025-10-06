package com.api.quizAI.core.exceptions;

import java.util.UUID;

public class UserNotFound extends RuntimeException {
    public UserNotFound(UUID userId)
    {
        super("Não foi possível encontrar usuário de id " + userId);
    }
}
