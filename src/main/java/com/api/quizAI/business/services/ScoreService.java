package com.api.quizAI.business.services;

import com.api.quizAI.core.domain.Answer;
import com.api.quizAI.core.domain.Score;
import com.api.quizAI.core.exceptions.ScoreNotFound;
import com.api.quizAI.infra.repository.ScoreRepository;
import com.api.quizAI.web.dto.AnswerRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoreService
{
    private final ScoreRepository scoreRepository;
    private final AnswerService answerService;

    public Score save(Score score)
    {
        log.info("saving score to database {} of user {}", score.getId(), score.getUserId());

        score = scoreRepository.save(score);

        log.info("persisted score {} in database of user {}", score.getId(), score.getUserId());

        return score;
    }

    public Score findById(UUID scoreId)
    {
        log.info("searching for score {}", scoreId);

        Score score = scoreRepository.findById(scoreId).orElseThrow(() -> new ScoreNotFound(scoreId));

        log.info("found score {}", scoreId);

        return score;
    }

    public void delete(UUID scoreId)
    {
        log.info("deleting score {}", scoreId);

        Score score = findById(scoreId);
        scoreRepository.delete(score);

        log.info("deleted score {}", scoreId);
    }

    public Integer calculatePlayerScore(UUID scoreId, AnswerRequestDTO answerRequest)
    {
        Answer answer = answerService.findById(answerRequest.answerId());
        if (answer.isCorrectAnswer())
        {
            Score score = findById(scoreId);
            score.addScore(10);
            scoreRepository.save(score);

            return score.getScore();
        }

        return 0;
    }
}
