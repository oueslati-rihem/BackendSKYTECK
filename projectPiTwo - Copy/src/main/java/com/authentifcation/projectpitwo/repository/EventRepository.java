package com.authentifcation.projectpitwo.repository;


import com.authentifcation.projectpitwo.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {


    List<Event> findByDate(Date date);

    List<Event> findByDateBetween(Date startDate, Date endDate);

    List<Event> findByUserId(Integer id);

    @Query("SELECT e, COUNT(p) " +
            "FROM Event e " +
            "LEFT JOIN Participationevent p ON e.numEvent = p.event.numEvent " +
            "GROUP BY e")
    List<Object[]> calculateEventParticipationStatistics();

    @Query("SELECT MONTH(e.date) as month, COUNT(e) as count " +
            "FROM Event e " +
            "GROUP BY MONTH(e.date)")
    List<Object[]> countEventsPerMonth();
    //coment
}
