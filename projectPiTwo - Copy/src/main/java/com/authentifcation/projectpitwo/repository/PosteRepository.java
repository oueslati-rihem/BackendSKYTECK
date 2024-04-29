package com.authentifcation.projectpitwo.repository;


import com.authentifcation.projectpitwo.entities.Poste;
import com.authentifcation.projectpitwo.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosteRepository extends JpaRepository<Poste,Long> {

    List<Poste> findAllByRoom(Room room);


    //findByUser
}
