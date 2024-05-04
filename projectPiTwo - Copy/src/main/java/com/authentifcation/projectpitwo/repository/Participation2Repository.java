package com.authentifcation.projectpitwo.repository;



import com.authentifcation.projectpitwo.entities.Event;
import com.authentifcation.projectpitwo.entities.Participationevent;
import com.authentifcation.projectpitwo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Participation2Repository extends JpaRepository<Participationevent,Long> {
    List<Participationevent> findByEvent(Event event);

    List<Participationevent> findByUserId(Integer Id);

    List<Participationevent> findByUserAndEvent(User user, Event event);


}
