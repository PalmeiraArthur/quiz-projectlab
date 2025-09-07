package com.api.quizAI.infra.repository;

import com.api.quizAI.core.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
}
