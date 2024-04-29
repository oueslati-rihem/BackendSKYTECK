package com.authentifcation.projectpitwo.serviceInterface;


import com.authentifcation.projectpitwo.entities.Event;

import java.util.Date;
import java.util.List;

public interface EventInterface {
    // Create
    void createEvent(Integer Id, Event event);

    // Read
    Event getEventById(Long NumEvent);
    List<Event> getAllEvents();

    // Update
    void updateEvent(Long NumEvent, Event event) throws Exception;

    // Delete
    void deleteEvent(Long NumEvent) throws Exception;
    List<Event> getEventsByDate(Date date);

    List<Event> getEventsByDateRange(Date startDate, Date endDate);

    List<Event> getEventsByUserId(Integer Id);
    List<Object[]> calculateEventParticipationStatistics();
    long countEvents();
}
