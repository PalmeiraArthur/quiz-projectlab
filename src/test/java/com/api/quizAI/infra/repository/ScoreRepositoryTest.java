package com.api.quizAI.infra.repository;

import com.api.quizAI.core.domain.Score;
import com.api.quizAI.core.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@DisplayName("Given the score repository")
class ScoreRepositoryTest
{
    @Autowired
    UserRepository userRepository;

    @Autowired
    ScoreRepository scoreRepository;

    @Nested
    @DisplayName("When searching for scores")
    class find
    {
        @Test
        @DisplayName("Then should find all scoreboards given the room id")
        void shouldFindAllScoreboardOnTheRoom()
        {
            User player1 = User.builder().username("p1").build();
            User player2 = User.builder().username("p2").build();
            userRepository.save(player1);
            userRepository.save(player2);

            UUID roomId = UUID.randomUUID();

            Score p1Scoreboard = Score.builder().user(player1).roomId(roomId).build();
            Score p2Scoreboard = Score.builder().user(player2).roomId(roomId).build();
            p1Scoreboard = scoreRepository.save(p1Scoreboard);
            p2Scoreboard = scoreRepository.save(p2Scoreboard);

            Set<Score> result = scoreRepository.findByRoomId(roomId);

            assertArrayEquals(new Score[]{p1Scoreboard, p2Scoreboard}, result.toArray());
        }
    }
}