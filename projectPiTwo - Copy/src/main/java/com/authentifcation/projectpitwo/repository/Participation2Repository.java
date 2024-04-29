package com.authentifcation.projectpitwo.repository;



import com.authentifcation.projectpitwo.entities.Event;
import com.authentifcation.projectpitwo.entities.Participation2;
import com.authentifcation.projectpitwo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Participation2Repository extends JpaRepository<Participation2,Long> {
    List<Participation2> findByEvent(Event event);

    List<Participation2> findByUserId(Integer Id);

    List<Participation2> findByUserAndEvent(User user, Event event);


}
