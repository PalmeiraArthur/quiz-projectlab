package com.api.quizAI.business.authorization;

import com.api.quizAI.core.domain.Room;
import com.api.quizAI.core.domain.User;
import com.api.quizAI.core.exceptions.UserIsNotRoomOwner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoomAuthorization
{
    public void verifyRoomOwner(Room room, User user)
    {
        if (! room.getOwner().getId().equals(user.getId()))
        {
            log.error("Unauthorized user action {} in room {}", user.getId(), room.getId());
            throw new UserIsNotRoomOwner();
        }
    }
}
