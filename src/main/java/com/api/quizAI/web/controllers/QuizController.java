package com.api.quizAI.web.controllers;

import com.api.quizAI.business.services.AIQuizGenerator;
import com.api.quizAI.core.domain.Quiz;
import com.api.quizAI.web.dto.QuizRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/quiz", produces = "application/json")
@Slf4j
@RequiredArgsConstructor
public class QuizController
{
    private final AIQuizGenerator quizGenerator;

    @PostMapping
    public ResponseEntity<Quiz> generateQuiz(@Valid @RequestBody QuizRequestDTO quizRequestDTO)
    {
        log.info("starting generate quiz request");

        Quiz quiz = quizGenerator.generate(quizRequestDTO.topic(), quizRequestDTO.numberOfQuestions(), quizRequestDTO.numberOfAnswers());

        log.info("successfully generated quiz request");

        return new ResponseEntity<>(quiz, HttpStatus.CREATED);
    }

}
