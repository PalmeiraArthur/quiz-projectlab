package com.api.quizAI.core.exceptions;

public class NoneCorrectAnswerFound extends RuntimeException {
    public NoneCorrectAnswerFound() {
        super("Não foi possível achar uma alternativa correta");
    }
}
