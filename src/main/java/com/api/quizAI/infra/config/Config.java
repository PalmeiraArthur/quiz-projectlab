package com.api.quizAI.infra.config;

import com.google.genai.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config
{
    @Bean
    public Client geminiClient()
    {
        return new Client();
    }

    @Bean("quizJsonFormat")
    public String quizJsonFormat()
    {
        return "{\"topic\": \"string\", \"questions\": [{\"value\": \"string\", \"answers\": [{\"value\": \"string\", \"isCorrectAnswer\": \"boolean\"}]}]}";
    }
}
