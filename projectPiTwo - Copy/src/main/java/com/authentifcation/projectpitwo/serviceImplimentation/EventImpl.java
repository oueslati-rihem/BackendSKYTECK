package com.authentifcation.projectpitwo.serviceImplimentation;


import com.authentifcation.projectpitwo.entities.Event;
import com.authentifcation.projectpitwo.entities.User;
import com.authentifcation.projectpitwo.repository.EventRepository;
import com.authentifcation.projectpitwo.repository.UserRepository;
import com.authentifcation.projectpitwo.serviceInterface.EventInterface;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class EventImpl implements EventInterface {
    EventRepository repo;
    UserRepository userRepo;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createEvent(Integer Id, Event event) {
        User user = userRepo.findById(Id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        event.setUser(user); // Set the user directly on the event
        repo.save(event);
    }

    @Override
    public Event getEventById(Long numEvent) {
        Optional<Event> optionalEvent = repo.findById(numEvent);
        return optionalEvent.orElse(null);
    }

    @Override
    public List<Event> getAllEvents() {
        return repo.findAll();
    }

    @Override
    public void updateEvent(Long numEvent, Event event) throws Exception {
        Optional<Event> optionalEvent = repo.findById(numEvent);
        if (optionalEvent.isPresent()) {
            Event existingEvent = optionalEvent.get();
            // Update fields of existingEvent with fields of event
            existingEvent.setNom(event.getNom());
            existingEvent.setDescription(event.getDescription());
            existingEvent.setDate(event.getDate());
            existingEvent.setNombreDePlace(event.getNombreDePlace());
            existingEvent.setPhotoUrl(event.getPhotoUrl());
            existingEvent.setLink(event.getLink());
            repo.save(existingEvent);
        } else {
            // Handle case where event with given id is not found
            // For example, you can throw a custom exception
            throw new Exception("Event not found with ID: " + numEvent);
        }
    }



    @Override
    public void deleteEvent(Long numEvent) throws Exception {
        if (repo.existsById(numEvent)) {
            repo.deleteById(numEvent);
        } else {
            // Handle the case where no event with the given ID is found
            // For example, you can throw a custom exception or log a message
            throw new Exception("Event not found with ID: " + numEvent);
        }
    }



    @Override
    public List<Event> getEventsByDate(Date date) {
        return repo.findByDate(date);
    }

    @Override
    public List<Event> getEventsByDateRange(Date startDate, Date endDate) {
        return repo.findByDateBetween(startDate, endDate);
    }

    @Override
    public List<Event> getEventsByUserId(Integer id) {
        return repo.findByUserId(id);
    }

    @Override
    public List<Object[]> calculateEventParticipationStatistics() {
        return repo.calculateEventParticipationStatistics();
    }

    @Override
    public long countEvents() {
        return repo.count();
    }

    @Override
    public Map<String, Integer> getNumberOfEventsPerMonth() {
        Map<String, Integer> eventsPerMonth = new HashMap<>();
        List<Object[]> results = repo.countEventsPerMonth();

        for (Object[] result : results) {
            int month = (int) result[0];
            int count = ((Number) result[1]).intValue();
            String monthName = getMonthName(month); // You need to implement this method
            eventsPerMonth.put(monthName, count);
        }
        return eventsPerMonth;
    }

    private String getMonthName(int month) {
        // Implement logic to get month name from month number (1-based)
        // Example implementation can use java.time.Month or SimpleDateFormat
        return "Month " + month; // Placeholder implementation

    }


}