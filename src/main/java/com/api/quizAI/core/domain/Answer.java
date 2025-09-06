package com.api.quizAI.core.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
@Table(name = "answers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Answer
{
    @Builder.Default
    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false, name = "answer_value")
    private String value;

    @Column(nullable = false, name = "is_correct_answer")
    private boolean isCorrectAnswer;
}
