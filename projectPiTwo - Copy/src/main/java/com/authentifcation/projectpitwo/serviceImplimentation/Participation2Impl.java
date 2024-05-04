package com.authentifcation.projectpitwo.serviceImplimentation;


import com.authentifcation.projectpitwo.entities.Event;
import com.authentifcation.projectpitwo.entities.Participationevent;
import com.authentifcation.projectpitwo.entities.ParticipationStatus;
import com.authentifcation.projectpitwo.entities.User;
import com.authentifcation.projectpitwo.repository.EventRepository;
import com.authentifcation.projectpitwo.repository.Participation2Repository;
import com.authentifcation.projectpitwo.repository.UserRepository;
import com.authentifcation.projectpitwo.serviceInterface.Participation2Interface;
import com.authentifcation.projectpitwo.util.EmailUtil2;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class Participation2Impl implements Participation2Interface {
    Participation2Repository repopart;
    UserRepository userRepo;
    EventRepository repo;

    @Autowired
    EmailUtil2 emailUtil;
    @Override
    public Participationevent participate(Integer userId, Long eventId) {
        // Find the event
        Event event = repo.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event Id"));

        // Find the user
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id"));

        // Check if the user has already participated in the event
        if (hasUserParticipatedInEvent(user, event)) {
            throw new IllegalArgumentException("User has already participated in the event");
        }

        // Check if event is full
        if (isEventFull(event)) {
            throw new IllegalArgumentException("Event is full, cannot participate");
        }

        // Create participation record
        Participationevent participationevent = new Participationevent();
        participationevent.setEvent(event);
        participationevent.setUser(user);
        participationevent.setStatus(ParticipationStatus.WAITING); // By default, participation is waiting

        return repopart.save(participationevent);
    }

    private boolean hasUserParticipatedInEvent(User user, Event event) {
        // Check if the user has already participated in the event
        List<Participationevent> participationevents = repopart.findByUserAndEvent(user, event);
        return !participationevents.isEmpty();
    }


    @Override
    public boolean isEventFull(Event event) {
        List<Participationevent> participationevent = repopart.findByEvent(event);
        return participationevent.size() >= event.getNombreDePlace();
    }

    @Override
    public List<Participationevent> getParticipationsByUserId(Integer Id) {
        return repopart.findByUserId(Id);
    }





    @Override
    public void acceptParticipation(Long idPart) throws MessagingException {
        Participationevent participationevent = repopart.findById(idPart)
                .orElseThrow(() -> new IllegalArgumentException("Invalid participation Id"));

        // Mark the participation as accepted
        participationevent.setStatus(ParticipationStatus.ACCEPTED);
        repopart.save(participationevent); // Save the updated participation

        // Get the associated user with the participation
        User user = participationevent.getUser();

        if (user != null) {
            // Obtain the event link from the associated event
            Event event = participationevent.getEvent();
            String eventLink = event != null ? event.getLink() : "";

            String subject = "Participation Accepted";
            String message = "Dear " + user.getUserName() + ",\n\n"
                    + "Your participation in the event has been accepted.\n"
                    + "Thank you for your participation!\n\n"
                    + "Best regards,\nThe Event Team";

            // Pass the event link to the sendEmail method
            emailUtil.sendEmail(user.getUserName(), subject, message, eventLink);
        } else {
            throw new IllegalStateException("No user associated with this participation.");
        }
    }


    @Override
    public void rejectParticipation(Long IdPart) {
        Participationevent participationevent = repopart.findById(IdPart).orElseThrow(() -> new IllegalArgumentException("Invalid participation Id"));
        participationevent.setStatus(ParticipationStatus.REJECTED); // Mark the participation as rejected
        repopart.save(participationevent); // Save the updated participation
    }
    @Override
    public Participationevent archiveParticipation(Long IdPart) {
        Participationevent participationevent = repopart.findById(IdPart)
                .orElseThrow(() -> new IllegalArgumentException("Invalid participation Id"));
        participationevent.setStatus(ParticipationStatus.ARCHIVED);
        return repopart.save(participationevent);
    }




    @Override
    public List<Participationevent> getParticipation() {
        return repopart.findAll();
    }


}
