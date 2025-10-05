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
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class User
{
    @Id
    private final UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String username;
}
