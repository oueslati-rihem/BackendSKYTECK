package com.authentifcation.projectpitwo.serviceImplimentation;


import com.authentifcation.projectpitwo.entities.Event;
import com.authentifcation.projectpitwo.entities.User;
import com.authentifcation.projectpitwo.repository.EventRepository;
import com.authentifcation.projectpitwo.repository.UserRepository;
import com.authentifcation.projectpitwo.serviceInterface.EventInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventImpl implements EventInterface {
    EventRepository repo;
    UserRepository userRepo;

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


}