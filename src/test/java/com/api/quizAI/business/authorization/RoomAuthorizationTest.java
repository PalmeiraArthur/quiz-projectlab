package com.api.quizAI.business.authorization;

import com.api.quizAI.core.domain.Room;
import com.api.quizAI.core.domain.User;
import com.api.quizAI.core.exceptions.UserIsNotRoomOwner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Given the room authorization service")
class RoomAuthorizationTest
{
    @InjectMocks
    RoomAuthorization roomAuthorization;

    @Nested
    @DisplayName("When checking the user")
    class ownerAuthorization
    {
        @Test
        @DisplayName("Then the owner should be able to make actions to the room")
        void shouldPassOwner()
        {
            User user = User.builder().username("owner").build();
            Room room = Room.builder().isPublic(true).owner(user).build();

            assertDoesNotThrow(() -> roomAuthorization.verifyRoomOwner(room, user));
        }

        @Test
        @DisplayName("Then should not allow another user to make actions to the room")
        void shouldStopNotRoomOwner()
        {
            User owner = User.builder().username("owner").build();
            Room room = Room.builder().isPublic(true).owner(owner).build();
            User user = User.builder().username("user").build();

            assertThrows(UserIsNotRoomOwner.class, () -> roomAuthorization.verifyRoomOwner(room, user));
        }
    }
}