package com.api.quizAI.business.services;

import com.api.quizAI.business.authorization.RoomAuthorization;
import com.api.quizAI.core.domain.Quiz;
import com.api.quizAI.core.domain.Room;
import com.api.quizAI.core.domain.User;
import com.api.quizAI.infra.repository.RoomRepository;
import com.api.quizAI.web.dto.UpdateRoomDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Given the room service")
class RoomServiceTest
{
    @Mock
    RoomRepository roomRepository;

    @Mock
    QuizService quizService;

    @Mock
    UserService userService;

    @Mock
    RoomAuthorization roomAuthorization;

    @InjectMocks
    RoomService roomService;

    @Nested
    @DisplayName("When the update method is called")
    class update
    {
        @Test
        @DisplayName("Then should update the maximum number of players allowed in the room")
        void shouldOnlyUpdateMaxNumberPlayerInTheRoom()
        {
            int updatedNumberOfPlayers = 15;
            var roomMock = Mockito.mock(Room.class);
            var userMock = User.builder().username("mock").build();

            Mockito.when(roomRepository.findById(roomMock.getId())).thenReturn(Optional.of(roomMock));
            Mockito.when(userService.findById(userMock.getId())).thenReturn(userMock);

            roomService.update(
                    new UpdateRoomDTO(null, updatedNumberOfPlayers, null, userMock.getId()),
                    roomMock.getId());

            Mockito.verify(roomRepository).save(roomMock);
            Mockito.verify(roomMock).setMaxNumberOfPlayers(updatedNumberOfPlayers);
            Mockito.verify(roomMock, Mockito.never()).setIsPublic(false);
            Mockito.verify(roomMock, Mockito.never()).setQuiz(Mockito.any());
        }

        @Test
        @DisplayName("Then should update where the room is public or private")
        void shouldOnlyUpdateIsPublic()
        {
            boolean updateToPrivate = false;
            var roomMock = Mockito.mock(Room.class);
            var userMock = User.builder().username("mock").build();

            Mockito.when(roomRepository.findById(roomMock.getId())).thenReturn(Optional.of(roomMock));
            Mockito.when(userService.findById(userMock.getId())).thenReturn(userMock);

            roomService.update(
                    new UpdateRoomDTO(updateToPrivate, null, null, userMock.getId()),
                    roomMock.getId());

            Mockito.verify(roomRepository).save(roomMock);
            Mockito.verify(roomMock).setIsPublic(updateToPrivate);
            Mockito.verify(roomMock, Mockito.never()).setMaxNumberOfPlayers(Mockito.any());
            Mockito.verify(roomMock, Mockito.never()).setQuiz(Mockito.any());
        }

        @Test
        @DisplayName("Then should update the Quiz of the room")
        void shouldOnlyUpdateTheQuiz()
        {
            Quiz quizMock = Quiz.builder().build();;
            var roomMock = Mockito.mock(Room.class);
            var userMock = User.builder().username("mock").build();

            Mockito.when(roomRepository.findById(roomMock.getId())).thenReturn(Optional.of(roomMock));
            Mockito.when(quizService.findById(quizMock.getId())).thenReturn(quizMock);
            Mockito.when(userService.findById(userMock.getId())).thenReturn(userMock);

            roomService.update(
                    new UpdateRoomDTO(null, null, quizMock.getId(), userMock.getId()),
                    roomMock.getId());

            Mockito.verify(quizService).findById(quizMock.getId());
            Mockito.verify(roomRepository).save(roomMock);
            Mockito.verify(roomMock).setQuiz(quizMock);
            Mockito.verify(roomMock, Mockito.never()).setIsPublic(Mockito.any());
            Mockito.verify(roomMock, Mockito.never()).setMaxNumberOfPlayers(Mockito.any());
        }
    }
}