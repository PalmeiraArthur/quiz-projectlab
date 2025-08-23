package com.api.quizAI.business.services;

import com.api.quizAI.core.domain.Quiz;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class GeminiAITest
{
    @Mock
    Client geminiClient;

    @InjectMocks
    private GeminiAI geminiAIService;

    @DisplayName("Then should send the response as a json to transform into Quiz object")
    @Test
    void shouldTransformJsonInObject() throws JsonProcessingException
    {
        String jsonString = "{\"topic\": \"string\", \"questions\": [{\"value\": \"string\", \"answers\": [{\"value\": \"string\", \"isCorrectAnswer\": true}]}]}";
        Quiz quiz = new ObjectMapper().readValue(jsonString, Quiz.class);
        System.out.println(quiz);
    }

}