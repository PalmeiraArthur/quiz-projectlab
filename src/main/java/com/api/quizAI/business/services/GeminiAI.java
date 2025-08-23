package com.api.quizAI.business.services;

import com.api.quizAI.core.domain.Quiz;
import com.api.quizAI.core.exceptions.UnableToConvertJsonToObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GeminiAI implements AIQuizGenerator
{
    private final Client geminiClient;
    private final String quizJsonFormat;
    private final String geminiModel;

    public GeminiAI(Client geminiClient, @Qualifier("quizJsonFormat") String quizJsonFormat, @Value("${ai.gemini.model}") String geminiModel)
    {
        this.geminiClient = geminiClient;
        this.quizJsonFormat = quizJsonFormat;
        this.geminiModel = geminiModel;
    }

    @Override
    public Quiz generate(String topic, int numberOfQuestions, int numberOfAnswers)
    {
        String request = "Crie um quiz com " + numberOfQuestions + " perguntas e " +
                numberOfAnswers + " alternativas para cada pergunta(contendo apenas uma certa) " +
                "sobre o tópico " + topic +
                ". A saída deve ser um único objeto JSON que siga exatamente esta estrutura: " + quizJsonFormat;

        log.info("Generating quiz for topic {} with request: {}", topic, request);

        GenerateContentResponse response = geminiClient.models.generateContent(geminiModel, request, null);
        String JsonString = cleanJson(response.text());

        try
        {
            return new ObjectMapper().readValue(JsonString, Quiz.class);
        }
        catch (JsonProcessingException e)
        {
            throw new UnableToConvertJsonToObject("Não foi possível converter JSON para classe Quiz");
        }
    }

    private String cleanJson(String raw)
    {
        raw = raw.trim();
        if (raw.startsWith("```"))
        {
            raw = raw.replaceFirst("(?s)```json\\s*", "");
            raw = raw.replaceAll("```$", "");
        }
        return raw.trim();
    }
}
