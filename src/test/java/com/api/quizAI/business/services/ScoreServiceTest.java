package com.api.quizAI.business.services;

import com.api.quizAI.core.domain.Score;
import com.api.quizAI.core.domain.User;
import com.api.quizAI.infra.repository.ScoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Given the score service")
class ScoreServiceTest
{
    @Mock
    ScoreRepository scoreRepository;

    @Mock
    AnswerService answerService;

    @Mock
    UserService userService;

    @InjectMocks
    ScoreService scoreService;

    @Nested
    @DisplayName("When find users scoreboard ordered is called")
    class findUsersScoreboardOrderedByScore
    {
        @Test
        @DisplayName("Then the scores should be ordered")
        void shouldReturnScoresOrdered()
        {
            UUID roomId = Mockito.mock(UUID.class);

            Score biggestScore = Score.builder().score(10).roomId(roomId).user(Mockito.mock(User.class)).build();
            Score middleScore = Score.builder().score(5).roomId(roomId).user(Mockito.mock(User.class)).build();
            Score smallerScore = Score.builder().score(2).roomId(roomId).user(Mockito.mock(User.class)).build();

            Mockito.when(scoreRepository.findByRoomId(roomId))
                    .thenReturn(Set.of(middleScore, biggestScore, smallerScore));

            List<Score> result = scoreService.findUsersScoreboardOrderedByScore(roomId);

            assertEquals(biggestScore, result.getFirst());
            assertEquals(middleScore, result.get(1));
            assertEquals(smallerScore, result.getLast());
        }
    }
}