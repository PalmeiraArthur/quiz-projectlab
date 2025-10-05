package com.api.quizAI.business.services;

import com.api.quizAI.core.domain.Answer;
import com.api.quizAI.core.exceptions.AnswerNotFound;
import com.api.quizAI.infra.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerService
{
    private final AnswerRepository answerRepository;

    public Answer findById(UUID answerId)
    {
        log.info("searching for answer {}", answerId);

        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new AnswerNotFound(answerId));

        log.info("found answer {}", answerId);

        return answer;
    }
}
