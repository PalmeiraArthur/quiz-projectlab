package com.api.quizAI.web.controllers;

import com.api.quizAI.business.services.QuestionService;
import com.api.quizAI.web.dto.BadRequestExample;
import com.api.quizAI.web.dto.CorrectAnswerIdDTO;
import com.api.quizAI.web.dto.ProblemDetailExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/questions", produces = "application/json")
@Slf4j
@RequiredArgsConstructor
public class QuestionController
{
    private final QuestionService questionService;

    @Operation(summary = "Get correct answer", description = "Return the id of the correct answer for the question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = CorrectAnswerIdDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ProblemDetailExample.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BadRequestExample.class))),
            @ApiResponse(responseCode = "424", description = "Failed Dependency", content = @Content(schema = @Schema(implementation = BadRequestExample.class)))
    })
    @GetMapping(value = "/{id}/correct-answer")
    public ResponseEntity<CorrectAnswerIdDTO> findCorrectAnswer(@PathVariable(value = "id") UUID questionId)
    {
        log.info("starting get correct answer for question {}", questionId);

        UUID correctAnswerId = questionService.findCorrectAnswer(questionId);

        log.info("successfully got correct answer {}", questionId);

        return new ResponseEntity<>(new CorrectAnswerIdDTO(correctAnswerId), HttpStatus.OK);
    }
}
