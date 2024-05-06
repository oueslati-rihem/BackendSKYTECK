package com.authentifcation.projectpitwo.serviceInterface;


import com.authentifcation.projectpitwo.entities.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IRoomService {

      Room addRoom (Room room);
      Room getRoom ( Long id);
      Room updateRoom (Room room);
      List<Room> rooms ();
      List<Room>publicRooms();
    public List<Room> privateRooms();
    //room by id of the creater ( what he did create )
    //room by the subscription to room()

    void removeRoom( Long numRoom);
    public Room createRoom(Room request, MultipartFile imageFile) throws IOException;
    //unsubscibe to room

}
