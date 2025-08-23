package com.api.quizAI.business.services;

import com.api.quizAI.core.domain.Quiz;
import com.api.quizAI.core.exceptions.QuizNotFound;
import com.api.quizAI.infra.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService
{
    private final QuizRepository quizRepository;

    private Quiz save(Quiz quiz)
    {
        log.info("Saving quiz to database {}", quiz.getId());

        quiz = quizRepository.save(quiz);

        log.info("persisted quiz {} in database", quiz.getId());

        return quiz;
    }

    public Quiz findById(UUID quizId)
    {
        log.info("searching for quiz {}", quizId);

        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new QuizNotFound(quizId));

        log.info("found quiz {}", quizId);

        return quiz;
    }

    public void delete(UUID quizId)
    {
        log.info("deleting quiz {}", quizId);

        Quiz quiz = findById(quizId);
        quizRepository.delete(quiz);

        log.info("deleted quiz {}", quizId);
    }

//    public Quiz generateQuiz(String topic, int numberOfQuestions, int numberOfAnswers)
//    {
//
//    }
}
