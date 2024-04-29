package com.authentifcation.projectpitwo.controller;


import com.authentifcation.projectpitwo.entities.Room;
import com.authentifcation.projectpitwo.repository.UserRepository;
import com.authentifcation.projectpitwo.serviceInterface.IRoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
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
@CrossOrigin()

public class RoomController {

    IRoomService iRoomService;
    UserRepository userRepository ;


    @PostMapping("/add")
    public Room addRoom(Room room) {
        return iRoomService.addRoom(room);
    }


   public static  String uploadDirectory =
           System.getProperty("user.dir")+"/src/main/resources/uploads" ;
    @PostMapping("/saveRoom/{userid}")
    public  Room saveRoom(@ModelAttribute Room room,
                          @RequestParam("image") MultipartFile file,@PathVariable  ("userid")Integer userid ) throws IOException {
        System.out.println("heeeeeeeeeer"+room);
        System.out.println("userId"+userid);
        String originalFilename= file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDirectory,originalFilename) ;
        Files.write(fileNameAndPath,file.getBytes());
        room.setImageUrl(Base64.getEncoder().encodeToString(file.getBytes()));
        room.setCreator(userRepository.findById(userid).orElse(null));
        return iRoomService.addRoom(room);

    }



    @PutMapping("/update")
    public Room updateRoom(@RequestBody Room room) {
        return iRoomService.updateRoom(room);
    }

    @GetMapping("/publicRoom")
    public List<Room> publicrooms() {
        return iRoomService.publicRooms();
    }
    @GetMapping("/privateRoom")
    public List<Room> privaterooms() {
        return iRoomService.privateRooms();
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
