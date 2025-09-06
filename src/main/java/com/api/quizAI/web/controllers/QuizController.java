package com.api.quizAI.web.controllers;

import com.api.quizAI.business.services.QuizService;
import com.api.quizAI.core.domain.Quiz;
import com.api.quizAI.web.dto.BadRequestExample;
import com.api.quizAI.web.dto.ProblemDetailExample;
import com.api.quizAI.web.dto.QuizRequestDTO;
import com.api.quizAI.web.dto.QuizResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private final QuizService quizService;

    @Operation(summary = "Generate quiz using AI model", description = "Generate quiz(questions and answers) using AI model")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = QuizResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ProblemDetailExample.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BadRequestExample.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity - AI model didn't send the json in the expected format to transform in Quiz object", content = @Content(schema = @Schema(implementation = ProblemDetailExample.class)))
    })
    @PostMapping
    public ResponseEntity<QuizResponseDTO> generateQuiz(@Valid @RequestBody QuizRequestDTO quizRequestDTO)
    {
        log.info("starting generate quiz request");

        Quiz quiz = quizService.generateQuiz(quizRequestDTO.topic(), quizRequestDTO.numberOfQuestions(), quizRequestDTO.numberOfAnswers());

        log.info("successfully generated quiz request");

        return new ResponseEntity<>(QuizResponseDTO.domainToDTO(quiz), HttpStatus.CREATED);
    }
}
