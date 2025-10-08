package com.api.quizAI.web.controllers;

import com.api.quizAI.business.services.RoomService;
import com.api.quizAI.core.domain.Room;
import com.api.quizAI.web.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(value = "/rooms", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class RoomController
{
    private final RoomService roomService;


    @Operation(summary = "Create room", description = "Create room to play the quiz")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = RoomCreationResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ProblemDetailExample.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BadRequestExample.class))),
    })
    @PostMapping
    public ResponseEntity<RoomCreationResponseDTO> createRoom(@Valid @RequestBody RoomRequestDTO roomRequestDTO)
    {
        log.info("starting room creation");

        Room room = roomService.save(Room.builder()
                .isPublic(roomRequestDTO.isPublic())
                .maxNumberOfPlayers(roomRequestDTO.maxNumberOfPlayersInRoom()).build(),
                roomRequestDTO.ownerId());

        log.info("successfully created room {}", room.getId());

        return new ResponseEntity<>(new RoomCreationResponseDTO(
                        room.getId(),
                        room.getRoomCode(),
                        room.getIsPublic(),
                        roomRequestDTO.maxNumberOfPlayersInRoom(),
                        room.getOwner()),
                HttpStatus.CREATED);
    }


    @Operation(summary = "Delete room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ProblemDetailExample.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BadRequestExample.class))),
    })
    @DeleteMapping(value = "/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable("id") UUID roomId, @Valid @RequestBody @NotNull UUID userId)
    {
        log.info("starting room delete request");

        roomService.delete(roomId, userId);

        log.info("successfully deleted room");
    }


    @Operation(summary = "Update room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ProblemDetailExample.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BadRequestExample.class))),
    })
    @PatchMapping(value = "/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRoom(
            @PathVariable("id") UUID roomId,
            @Valid @RequestBody UpdateRoomDTO updateRoomDTO)
    {
        log.info("starting room update request {}", roomId);

        roomService.update(updateRoomDTO, roomId);

        log.info("successfully updated room {}", roomId);
    }

    @Operation(summary = "Find public rooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PublicRoomsResponseDTO.class)))),
    })
    @GetMapping
    public ResponseEntity<Set<PublicRoomsResponseDTO>> findPublicRooms()
    {
        log.info("starting find public rooms request");

        Set<Room> publicRooms = roomService.findRooms();

        log.info("successfully found {} rooms", publicRooms.size());

        return new ResponseEntity<>(PublicRoomsResponseDTO.fromDomainToDTO(publicRooms), HttpStatus.OK);
    }
}
