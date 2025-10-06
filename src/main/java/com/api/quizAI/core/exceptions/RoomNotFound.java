package com.api.quizAI.core.exceptions;

public class RoomNotFound extends RuntimeException
{
    public RoomNotFound(String code)
    {
        super("Não foi possível encontrar a sala de código " + code);
    }
}
