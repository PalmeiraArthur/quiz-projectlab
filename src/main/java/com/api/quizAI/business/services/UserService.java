package com.api.quizAI.business.services;

import com.api.quizAI.core.domain.User;
import com.api.quizAI.core.exceptions.UserNotFound;
import com.api.quizAI.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService
{
    private final UserRepository userRepository;

    public User save(User user)
    {
        log.info("Saving user to database {}", user.getId());

        user = userRepository.save(user);

        log.info("persisted user {} in database", user.getId());

        return user;
    }

    public User findById(UUID userId)
    {
        log.info("searching for user {}", userId);

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));

        log.info("found user {}", userId);

        return user;
    }

    public void delete(UUID userId)
    {
        log.info("deleting user {}", userId);

        User user = findById(userId);
        userRepository.delete(user);

        log.info("deleted user {}", userId);
    }
}
