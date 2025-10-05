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
@Table(name = "scores")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Score
{
    @Id
    private final UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private int score = 0;

    @Column(nullable = false, name = "user_id")
    private UUID userId;

    @Column(nullable = false, name = "room_id")
    private UUID roomId;

    public void addScore(int score)
    {
        this.score += score;
    }
}
