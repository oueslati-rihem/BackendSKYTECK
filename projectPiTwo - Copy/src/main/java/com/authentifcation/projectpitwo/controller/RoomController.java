package com.authentifcation.projectpitwo.controller;


import com.authentifcation.projectpitwo.entities.Room;
import com.authentifcation.projectpitwo.repository.RoomRepository;
import com.authentifcation.projectpitwo.repository.UserRepository;
import com.authentifcation.projectpitwo.serviceInterface.IRoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
@Tag(name = "Room management " )
@RestController
@RequestMapping("/room")
@AllArgsConstructor
public class RoomController {

    IRoomService iRoomService;
    UserRepository userRepository ;
    RoomRepository roomRepository ;


    @PostMapping("/add")
    public Room addRoom(Room room) {
        return iRoomService.addRoom(room);
    }

    @GetMapping("/get/{roomId}")
    public Room getRoom(@PathVariable("roomId") Long roomId) {
        return iRoomService.getRoom(roomId);
    }


   public static  String uploadDirectory =
           System.getProperty("user.dir")+"/src/main/resources/uploads" ;
    @PostMapping("/saveRoom/{userid}")
    public  Room saveRoom(@ModelAttribute Room room,
                          @RequestParam("image") MultipartFile file,
                          @PathVariable  ("userid")Integer userid ) throws IOException {
        System.out.println("heeeeeeeeeer"+room);
        System.out.println("userId"+userid);
        String originalFilename= file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDirectory,originalFilename) ;
        //Files.write(fileNameAndPath,file.getBytes());
        room.setImageUrl(Base64.getEncoder().encodeToString(file.getBytes()));
        room.setCreator(userRepository.findById(userid).orElse(null));
        return iRoomService.addRoom(room);

    }



    @PutMapping("/update/{roomId}")
    public ResponseEntity<Room> updateRoom(@RequestBody Room room,@PathVariable("roomId") Long roomId) {
        // Check if the provided room ID is null or zero
        if (roomId == null || roomId == 0) {
            return ResponseEntity.badRequest().build();
        }

        // Check if the room with the provided ID exists
        Room existingRoom = iRoomService.getRoom(roomId);
        if (existingRoom == null) {
            return ResponseEntity.notFound().build();
        }

        // Update the existing room with the provided data
        existingRoom.setName(room.getName());
        existingRoom.setDescription(room.getDescription());
        existingRoom.setCapacity(room.getCapacity());

        // Update other properties as needed

        // Save the updated room
        Room updatedRoom = iRoomService.updateRoom(existingRoom);

        // Return the updated room with a success status code
        return ResponseEntity.ok(updatedRoom);
    }

    @GetMapping("/publicRoom")
    public List<Room> publicrooms() {
        return iRoomService.publicRooms();
    }
    @GetMapping("/privateRoom")
    public List<Room> privaterooms() {
        return iRoomService.privateRooms();
    }
    @GetMapping("/allrooms")
    public List<Room> allrooms() {
        return roomRepository.findAll();
    }

    @DeleteMapping("delete/{numRoom}")
    public void removeRoom(@PathVariable("numRoom") Long numRoom) {
        iRoomService.removeRoom(numRoom);
    }



    private Room convertToResponseDTO(Room room) {
        Room responseDTO = new Room();
        responseDTO.setRoomId(room.getRoomId());
        responseDTO.setName(room.getName());
        responseDTO.setDescription(room.getDescription());
        responseDTO.setImageUrl(room.getImageUrl());
        return responseDTO;
    }



}
