package com.api.quizAI.core.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
@Table(name = "quizzes")
@Getter
@NoArgsConstructor
@ToString
public class Quiz
{
    @Id
    private final UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String topic;

    @OneToMany(targetEntity = Question.class, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Set<Question> questions;

    private Quiz(Builder quiz)
    {
        this.topic = quiz.topic;
        this.questions = quiz.questions;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private String topic;
        private Set<Question> questions;

        public Builder(){}

        public Builder topic(String name)
        {
            this.topic = name.substring(0, 1).toUpperCase() + name.substring(1);
            return this;
        }

        public Builder questions(Set<Question> questions)
        {
            this.questions = questions;
            return this;
        }

        public Quiz build()
        {
            return new Quiz(this);
        }
    }
}