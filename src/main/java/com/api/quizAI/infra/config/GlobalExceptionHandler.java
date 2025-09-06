package com.api.quizAI.infra.config;

import com.api.quizAI.core.exceptions.QuizNotFound;
import com.api.quizAI.core.exceptions.UnableToConvertJsonToObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleInvalidMethodArgument(MethodArgumentNotValidException exception)
    {
        List<HashMap<String, String>> errorMessages = new ArrayList<>();
        exception.getFieldErrors().forEach(error -> {
            HashMap<String, String> error_map = new HashMap<>();
            error_map.put("message", error.getDefaultMessage());
            errorMessages.add(error_map);
        });

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail("Parâmetros com valores inválidos");
        problemDetail.setTitle("Parâmetro inválido");
        problemDetail.setProperty("errors", errorMessages);

        return problemDetail;
    }

    @ExceptionHandler(QuizNotFound.class)
    public ProblemDetail handleQuizNotFoundException(QuizNotFound exception)
    {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problemDetail.setTitle("Quiz não encontrado");
        return problemDetail;
    }

    @ExceptionHandler(UnableToConvertJsonToObject.class)
    public ProblemDetail handleUnableToConvertJsonToObjectException(UnableToConvertJsonToObject exception)
    {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage());
        problemDetail.setTitle("Erro ao receber JSON do Quiz");
        return problemDetail;
    }
}
