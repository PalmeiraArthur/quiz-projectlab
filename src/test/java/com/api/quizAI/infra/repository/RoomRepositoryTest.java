package com.api.quizAI.infra.repository;

import com.api.quizAI.core.domain.Room;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@DisplayName("Given the room repository")
class RoomRepositoryTest
{
    @Autowired
    RoomRepository roomRepository;

    @Nested
    @DisplayName("When saving the room")
    class save
    {
        @Test
        @DisplayName("Then should persist room")
        void shouldSaveRoom()
        {
            Room room = Room.builder().isPublic(true).build();

            room = roomRepository.save(room);

            assertNotNull(room.getId());
            assertNotNull(room.getRoomCode());
            assertEquals(1, roomRepository.findAll().size());
        }
    }

    @Nested
    @DisplayName("When find the room")
    class find
    {
        @Test
        @DisplayName("Then should find by room code")
        void shouldFindByCode()
        {
            Room room = Room.builder().isPublic(true).build();
            roomRepository.save(room);

            assertTrue(roomRepository.findByRoomCode(room.getRoomCode()).isPresent());
        }

        @Test
        @DisplayName("Then should find only rooms that are public")
        void shouldFindOnlyPublicRooms()
        {
            Room publicRoom = Room.builder().isPublic(true).build();
            Room privateRoom = Room.builder().isPublic(false).build();
            roomRepository.save(publicRoom);
            roomRepository.save(privateRoom);

            assertEquals(2, roomRepository.findAll().size());
            assertEquals(1, roomRepository.findByIsPublicTrue().size());
        }
    }
}