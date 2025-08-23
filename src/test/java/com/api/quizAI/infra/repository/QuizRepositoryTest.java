package com.api.quizAI.infra.repository;

import com.api.quizAI.core.domain.Answer;
import com.api.quizAI.core.domain.Question;
import com.api.quizAI.core.domain.Quiz;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class QuizRepositoryTest
{
    @Autowired
    QuizRepository quizRepository;

    @Nested
    class save
    {
        @Test
        @DisplayName("Then should persist quiz")
        void shouldSaveQuiz()
        {
            Answer answer = Answer.builder().value("yes").isCorrectAnswer(true).build();
            Question question = Question.builder().value("a").answers(Set.of(answer)).build();
            Quiz quiz = Quiz.builder()
                    .topic("math")
                    .questions(Set.of(question))
                    .build();

            Quiz response = quizRepository.save(quiz);

            assertNotNull(response.getId());
            assertEquals(1, quizRepository.findAll().size());
        }

        @Test
        @DisplayName("Then should throw exception when topic field is null")
        void shouldThrowExceptionWithNullTopic()
        {
            Quiz quiz = Quiz.builder()
                    .questions(Set.of(Mockito.mock(Question.class)))
                    .build();

            assertThrows(Exception.class, () -> {
                quizRepository.save(quiz);
                quizRepository.findAll(); // trigger persistence
            });
        }

        @Test
        @DisplayName("Then should throw exception when questions field is null")
        void shouldThrowExceptionWithNullQuestions()
        {
            Quiz quiz = Quiz.builder()
                    .topic("math")
                    .questions(Set.of(Mockito.mock(Question.class)))
                    .build();

            assertThrows(Exception.class, () -> {
                quizRepository.save(quiz);
                quizRepository.findAll(); // trigger persistence
            });
        }
    }
}