package com.api.quizAI.core.exceptions;

public class UserIsNotRoomOwner extends RuntimeException
{
    public UserIsNotRoomOwner() {
        super("Apenas o dono da sala pode realizar essa ação");
    }
}
