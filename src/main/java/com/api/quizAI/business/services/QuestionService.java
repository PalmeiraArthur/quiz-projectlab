package com.api.quizAI.business.services;

import com.api.quizAI.core.domain.Answer;
import com.api.quizAI.core.domain.Question;
import com.api.quizAI.core.exceptions.NoneCorrectAnswerFound;
import com.api.quizAI.core.exceptions.QuestionNotFound;
import com.api.quizAI.infra.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService
{
    private final QuestionRepository questionRepository;

    public Question findById(UUID questionId)
    {
        log.info("searching for question {}", questionId);

        Question question = questionRepository.findById(questionId).orElseThrow(() -> new QuestionNotFound(questionId));

        log.info("found question {}", questionId);

        return question;
    }

    public UUID findCorrectAnswer(UUID questionId)
    {
        Question question = findById(questionId);

        for (Answer answer: question.getAnswers())
        {
            if (answer.isCorrectAnswer())
            {
                return answer.getId();
            }
        }

        throw new NoneCorrectAnswerFound();
    }
}
