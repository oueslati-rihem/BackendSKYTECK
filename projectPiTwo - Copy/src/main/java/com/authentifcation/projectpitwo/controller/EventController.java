package com.authentifcation.projectpitwo.controller;


import com.authentifcation.projectpitwo.entities.Event;
import com.authentifcation.projectpitwo.serviceInterface.EventInterface;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/event")
@CrossOrigin
public class EventController {
EventInterface eventInterface;
    @PostMapping("/add/{Id}")
    public ResponseEntity<?> createEvent(
            @PathVariable Integer Id,
            @RequestBody Event event) {
        try {
            eventInterface.createEvent(Id, event);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Failed to create event: " + e.getMessage());
        }
    }
@GetMapping("/get/{numEvent}")

public Object getEventById(@PathVariable("numEvent") Long numEvent) {
    Event event = eventInterface.getEventById(numEvent);
    if (event != null) {
        return event;
    } else {
        return "Event not found for ID: " + numEvent;
    }
    }
@GetMapping("/getevent")
    public List<Event> getAllEvents() {
        return eventInterface.getAllEvents();
    }
    @PutMapping("/update/{numEvent}")
    public ResponseEntity<?> updateEvent(@PathVariable("numEvent") Long numEvent, @RequestBody Event event) {
        try {
            eventInterface.updateEvent(numEvent, event);
            return ResponseEntity.ok("Event updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating event: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{numEvent}")
    public ResponseEntity<?> deleteEvent(@PathVariable("numEvent") Long numEvent) {
        try {
            eventInterface.deleteEvent(numEvent);
            return ResponseEntity.ok("Event deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting event: " + e.getMessage());
        }
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<?> getEventsByDate(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        try {
            // Set the time portion of the date to midnight (00:00:00)
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date startDate = calendar.getTime();

            // Set the time portion of the next day to midnight (00:00:00)
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date endDate = calendar.getTime();

            List<Event> events = eventInterface.getEventsByDateRange(startDate, endDate);
            if (!events.isEmpty()) {
                return ResponseEntity.ok(events);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No events found for date: " + date);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve events by date: " + e.getMessage());
        }
    }
    @GetMapping("/user/{Id}")
    public ResponseEntity<List<Event>> getEventsByUserId(@PathVariable Integer Id) {
        List<Event> events = eventInterface.getEventsByUserId(Id);
        return ResponseEntity.ok(events);
    }
    @GetMapping("/participation-statistics")
    public List<Object[]> getEventParticipationStatistics() {
        return eventInterface.calculateEventParticipationStatistics();
    }
    @GetMapping("/events/count")
    public ResponseEntity<Long> countEvents() {
        long count = eventInterface.countEvents();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/events/numberOfEventsPerMonth")
    public Map<String, Integer> getNumberOfEventsPerMonth() {
        return eventInterface.getNumberOfEventsPerMonth();
    }
}
