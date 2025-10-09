package com.api.quizAI.web.controllers;

import com.api.quizAI.business.services.ScoreService;
import com.api.quizAI.core.domain.Score;
import com.api.quizAI.web.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/scores", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class ScoreController
{
    private final ScoreService scoreService;

    @Operation(summary = "Create user scoreboard", description = "Endpoint to create a user scoreboard when it joins a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = Score.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ProblemDetailExample.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BadRequestExample.class))),
    })
    @PostMapping(value = "/create_scoreboard", consumes = "application/json")
    public ResponseEntity<Score> createUserScoreBoard(@Valid @RequestBody CreateScoreRequestDTO createScoreboardRequest)
    {
        log.info("starting scoreboard creation");

        Score score = scoreService.save(createScoreboardRequest);

        log.info("successfully created scoreboard {}", score.getId());

        return new ResponseEntity<>(score, HttpStatus.CREATED);
    }


    @Operation(summary = "Delete scoreboard")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ProblemDetailExample.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BadRequestExample.class))),
    })
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable("id") UUID scoreId)
    {
        log.info("starting scoreboard delete request");

        scoreService.delete(scoreId);

        log.info("successfully deleted scoreboard");
    }

    @Operation(summary = "Calculate user answer points", description = "Return the points earned after answer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = ScoreResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ProblemDetailExample.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BadRequestExample.class))),
    })
    @PatchMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<ScoreResponseDTO> calculateAnswerScore(
            @PathVariable("id") UUID scoreId,
            @Valid @RequestBody AnswerRequestDTO answerRequest)
    {
        log.info("starting answer points calculation {}", scoreId);

        Integer pointsEarned = scoreService.calculatePlayerScore(scoreId, answerRequest);

        log.info("successfully calculate points {}", scoreId);

        return new ResponseEntity<>(new ScoreResponseDTO(pointsEarned), HttpStatus.OK);
    }


    @Operation(summary = "Get users scoreboard", description = "Return each user and score ordered")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserScoreboardResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ProblemDetailExample.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BadRequestExample.class))),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<List<UserScoreboardResponse>> GetAllUsersAndScoreOrdered(@PathVariable("id") UUID roomId)
    {
        log.info("starting get users scoreboard for room {}", roomId);

        List<Score> scoresOrdered = scoreService.findUsersScoreboardOrderedByScore(roomId);

        log.info("successfully got users scoreboard ordered {}", roomId);

        return new ResponseEntity<>(
                scoresOrdered.stream().map(score -> new UserScoreboardResponse(score.getId(), score.getScore(), score.getUser())).toList(),
                HttpStatus.OK);
    }
}
