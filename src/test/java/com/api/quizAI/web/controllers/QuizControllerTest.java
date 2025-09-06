package com.api.quizAI.web.controllers;

import com.api.quizAI.business.services.QuizService;
import com.api.quizAI.core.domain.Answer;
import com.api.quizAI.core.domain.Question;
import com.api.quizAI.core.domain.Quiz;
import com.api.quizAI.core.exceptions.QuizNotFound;
import com.api.quizAI.core.exceptions.UnableToConvertJsonToObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(QuizController.class)
@DisplayName("Given the Quiz controller")
class QuizControllerTest
{
    @MockitoBean
    QuizService quizService;

    @Autowired
    MockMvc mockMvc;

    @Nested
    @DisplayName("When requested to generate a quiz")
    class generateQuiz
    {
        @Test
        @DisplayName("Then should return 201 CREATED")
        void shouldCreateQuiz() throws Exception
        {
            String payload = """
                {
                    "topic": "a",
                    "numberOfQuestions": 1,
                    "numberOfAnswers": 1
                }
                """;

            Mockito.when(quizService.generateQuiz(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
                    .thenReturn(Quiz.builder().questions(Set.of(Question.builder().answers(Set.of(Answer.builder().build())).build())).build());

            mockMvc.perform(MockMvcRequestBuilders.post("/quiz")
                            .contentType("application/json")
                            .content(payload))
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        }

        @Nested
        @DisplayName("If 400 BAD REQUEST is returned")
        class badRequest
        {
            @Test
            @DisplayName("Then the topic field should is blank")
            void shouldAssertTopicFieldNotBlank() throws Exception
            {
                String payload = """
                {
                    "topic": "",
                    "numberOfQuestions": 1,
                    "numberOfAnswers": 1
                }
                """;

                mockMvc.perform(MockMvcRequestBuilders.post("/quiz")
                                .contentType("application/json")
                                .content(payload))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message")
                                .value("O tópico para as questões não deve estar em branco"));
            }

            @Test
            @DisplayName("Then the topic field should is null")
            void shouldAssertTopicFieldNotNull() throws Exception
            {
                String payload = """
                {
                    "numberOfQuestions": 1,
                    "numberOfAnswers": 1
                }
                """;

                mockMvc.perform(MockMvcRequestBuilders.post("/quiz")
                                .contentType("application/json")
                                .content(payload))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message")
                                .value("O tópico para as questões não deve estar em branco"));
            }

            @Test
            @DisplayName("Then the number of questions field is null")
            void shouldThrowErrorWhenNumberOfQuestionsFieldIsNull() throws Exception
            {
                String payload = """
                {
                    "topic": "a",
                    "numberOfAnswers": 1
                }
                """;

                mockMvc.perform(MockMvcRequestBuilders.post("/quiz")
                                .contentType("application/json")
                                .content(payload))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message")
                                .value("O número de questões deve ser indicado"));
            }

            @Test
            @DisplayName("Then the number of questions field is null")
            void shouldThrowErrorWhenNumberOfQuestionsFieldIsZero() throws Exception
            {
                String payload = """
                {
                    "topic": "a",
                    "numberOfQuestions": 0,
                    "numberOfAnswers": 1
                }
                """;

                mockMvc.perform(MockMvcRequestBuilders.post("/quiz")
                                .contentType("application/json")
                                .content(payload))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message")
                                .value("O número de perguntas deve ser positivo"));
            }

            @Test
            @DisplayName("Then the number of questions field is null")
            void shouldThrowErrorWhenNumberOfQuestionsFieldIsNegative() throws Exception
            {
                String payload = """
                {
                    "topic": "a",
                    "numberOfQuestions": -1,
                    "numberOfAnswers": 1
                }
                """;

                mockMvc.perform(MockMvcRequestBuilders.post("/quiz")
                                .contentType("application/json")
                                .content(payload))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message")
                                .value("O número de perguntas deve ser positivo"));
            }

            @Test
            @DisplayName("Then the number of questions field is greater than 15")
            void shouldThrowErrorWhenNumberOfQuestionsFieldIsGreaterThan15() throws Exception
            {
                String payload = """
                {
                    "topic": "a",
                    "numberOfQuestions": 16,
                    "numberOfAnswers": 1
                }
                """;

                mockMvc.perform(MockMvcRequestBuilders.post("/quiz")
                                .contentType("application/json")
                                .content(payload))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message")
                                .value("O número máximo de perguntas deve ser 15"));
            }

            @Test
            @DisplayName("Then the number of questions field is null")
            void shouldThrowErrorWhenNumberOfAnswersFieldIsNull() throws Exception
            {
                String payload = """
                {
                    "topic": "a",
                    "numberOfQuestions": 1
                }
                """;

                mockMvc.perform(MockMvcRequestBuilders.post("/quiz")
                                .contentType("application/json")
                                .content(payload))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message")
                                .value("O número de alternativas deve ser indicado"));
            }

            @Test
            @DisplayName("Then the number of questions field is negative")
            void shouldThrowErrorWhenNumberOfAnswersFieldIsNegative() throws Exception
            {
                String payload = """
                {
                    "topic": "a",
                    "numberOfQuestions": 1,
                    "numberOfAnswers": -1
                }
                """;

                mockMvc.perform(MockMvcRequestBuilders.post("/quiz")
                                .contentType("application/json")
                                .content(payload))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message")
                                .value("O número de alternativas deve ser positivo"));
            }

            @Test
            @DisplayName("Then the number of questions field is greater than 6")
            void shouldThrowErrorWhenNumberOfAnswersFieldIsGreaterThan6() throws Exception
            {
                String payload = """
                {
                    "topic": "a",
                    "numberOfQuestions": 1,
                    "numberOfAnswers": 7
                }
                """;

                mockMvc.perform(MockMvcRequestBuilders.post("/quiz")
                                .contentType("application/json")
                                .content(payload))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message")
                                .value("O número máximo de alternativas deve ser 6"));
            }
        }

        @Nested
        @DisplayName("If 404 NOT FOUND is returned")
        class notFound
        {
            @Test
            @DisplayName("Then the quiz was not found in database")
            void shouldThrow404WhenNotFound() throws Exception
            {
                String payload = """
                {
                    "topic": "a",
                    "numberOfQuestions": 1,
                    "numberOfAnswers": 1
                }
                """;

                Mockito.when(quizService.generateQuiz(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
                                .thenThrow(new QuizNotFound(UUID.randomUUID()));

                mockMvc.perform(MockMvcRequestBuilders.post("/quiz")
                                .contentType("application/json")
                                .content(payload))
                        .andExpect(MockMvcResultMatchers.status().isNotFound())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.title")
                                .value("Quiz não encontrado"));
            }
        }

        @Nested
        @DisplayName("If 422 UNPROCESSABLE ENTITY is returned")
        class unprocessableEntity
        {
            @Test
            @DisplayName("Then the AI model didn't send the json in the expected format to transform in Quiz object")
            void shouldThrow422WhenJsonCanNotBeConvertedToObject() throws Exception
            {
                String payload = """
                {
                    "topic": "a",
                    "numberOfQuestions": 1,
                    "numberOfAnswers": 1
                }
                """;

                Mockito.when(quizService.generateQuiz(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
                        .thenThrow(new UnableToConvertJsonToObject(""));

                mockMvc.perform(MockMvcRequestBuilders.post("/quiz")
                                .contentType("application/json")
                                .content(payload))
                        .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.title")
                                .value("Erro ao receber JSON do Quiz"));
            }
        }
    }
}