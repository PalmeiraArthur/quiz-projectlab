package com.api.quizAI.web.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserRequestDTO(
        @Length(max = 30, message = "O nome de usuário deve ter no máximo 30 caracteres")
        @NotBlank(message = "O nome do usuário não pode estar em branco")
        String username
) {
}
