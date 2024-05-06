package com.authentifcation.projectpitwo.repository;

import com.authentifcation.projectpitwo.entities.Room;
import com.authentifcation.projectpitwo.entities.TypeRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository  extends JpaRepository<Room,Long> {

    /*Room findByName(String name );*/
    Room findRoomByRoomId(Long roomId);
    List<Room>findByTypeRoom(TypeRoom typeRoom);
}
