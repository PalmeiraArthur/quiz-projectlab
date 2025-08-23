package com.api.quizAI.business.services;

import com.api.quizAI.core.domain.Quiz;
import org.springframework.stereotype.Service;

@Service
public interface AIQuizGenerator
{
    Quiz generate(String topic, int numberOfQuestions, int numberOfAnswers);
}
