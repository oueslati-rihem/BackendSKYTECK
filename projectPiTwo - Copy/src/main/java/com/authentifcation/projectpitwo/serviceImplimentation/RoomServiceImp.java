package com.authentifcation.projectpitwo.serviceImplimentation;


import com.authentifcation.projectpitwo.entities.Room;
import com.authentifcation.projectpitwo.entities.TypeRoom;
import com.authentifcation.projectpitwo.repository.RoomRepository;
import com.authentifcation.projectpitwo.serviceInterface.IRoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceImp implements IRoomService {
    RoomRepository roomRepository ;

    @Override
    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room getRoom(Long id) {
        return roomRepository.findRoomByRoomId(id);
    }

    @Override
    public Room updateRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public List<Room> rooms() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> publicRooms() {
        return roomRepository.findByTypeRoom(TypeRoom.PUBLIC);
    }
    @Override
    public List<Room> privateRooms() {
        return roomRepository.findByTypeRoom(TypeRoom.PRIVATE);
    }

    @Override
    public void removeRoom(Long numRoom) {
        roomRepository.deleteById( numRoom);

    }

    @Override
    public Room createRoom(Room request, MultipartFile imageFile) throws IOException {
        return null;
    }

}
