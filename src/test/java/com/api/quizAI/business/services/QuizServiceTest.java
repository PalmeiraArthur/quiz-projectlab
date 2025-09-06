package com.api.quizAI.business.services;

import com.api.quizAI.core.domain.Quiz;
import com.api.quizAI.infra.repository.QuizRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Given the quiz service")
class QuizServiceTest
{
    @Mock
    QuizRepository quizRepository;

    @Mock
    AIQuizGenerator quizGenerator;

    @InjectMocks
    QuizService quizService;

    @Nested
    @DisplayName("When the generate quiz method is called")
    class generateQuiz
    {
        @Test
        @DisplayName("Then should trigger generate quiz method")
        public void shouldCallAIGeneratorService()
        {
            Quiz quizMock = Quiz.builder().build();

            Mockito.when(quizGenerator.generate(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(quizMock);
            Mockito.when(quizRepository.save(Mockito.any(Quiz.class))).thenReturn(quizMock);

            quizService.generateQuiz(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());

            Mockito.verify(quizGenerator).generate(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
        }

        @Test
        @DisplayName("Then should persist quiz in database")
        public void shouldSaveQuizInDatabase()
        {
            Quiz quizMock = Quiz.builder().build();

            Mockito.when(quizGenerator.generate(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(quizMock);
            Mockito.when(quizRepository.save(Mockito.any(Quiz.class))).thenReturn(quizMock);

            quizService.generateQuiz(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());

            Mockito.verify(quizRepository).save(quizMock);
        }
    }
}