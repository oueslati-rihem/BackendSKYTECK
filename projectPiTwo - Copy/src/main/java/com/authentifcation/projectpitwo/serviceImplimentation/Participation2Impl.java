package com.authentifcation.projectpitwo.serviceImplimentation;


import com.authentifcation.projectpitwo.entities.Event;
import com.authentifcation.projectpitwo.entities.Participation2;
import com.authentifcation.projectpitwo.entities.ParticipationStatus;
import com.authentifcation.projectpitwo.entities.User;
import com.authentifcation.projectpitwo.repository.EventRepository;
import com.authentifcation.projectpitwo.repository.Participation2Repository;
import com.authentifcation.projectpitwo.repository.UserRepository;
import com.authentifcation.projectpitwo.serviceInterface.Participation2Interface;
import com.authentifcation.projectpitwo.util.EmailUtil;
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
    public Participation2 participate(Integer userId, Long eventId) {
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
        Participation2 participation2 = new Participation2();
        participation2.setEvent(event);
        participation2.setUser(user);
        participation2.setStatus(ParticipationStatus.WAITING); // By default, participation is waiting

        return repopart.save(participation2);
    }

    private boolean hasUserParticipatedInEvent(User user, Event event) {
        // Check if the user has already participated in the event
        List<Participation2> participation2s = repopart.findByUserAndEvent(user, event);
        return !participation2s.isEmpty();
    }


    @Override
    public boolean isEventFull(Event event) {
        List<Participation2> participation2 = repopart.findByEvent(event);
        return participation2.size() >= event.getNombreDePlace();
    }

    @Override
    public List<Participation2> getParticipationsByUserId(Integer Id) {
        return repopart.findByUserId(Id);
    }





    @Override
    public void acceptParticipation(Long idPart) throws MessagingException {
        Participation2 participation2 = repopart.findById(idPart)
                .orElseThrow(() -> new IllegalArgumentException("Invalid participation Id"));

        // Mark the participation as accepted
        participation2.setStatus(ParticipationStatus.ACCEPTED);
        repopart.save(participation2); // Save the updated participation

        // Get the associated user with the participation
        User user = participation2.getUser();

        if (user != null) {
            // Obtain the event link from the associated event
            Event event = participation2.getEvent();
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
        Participation2 participation2 = repopart.findById(IdPart).orElseThrow(() -> new IllegalArgumentException("Invalid participation Id"));
        participation2.setStatus(ParticipationStatus.REJECTED); // Mark the participation as rejected
        repopart.save(participation2); // Save the updated participation
    }
    @Override
    public Participation2 archiveParticipation(Long IdPart) {
        Participation2 participation2 = repopart.findById(IdPart)
                .orElseThrow(() -> new IllegalArgumentException("Invalid participation Id"));
        participation2.setStatus(ParticipationStatus.ARCHIVED);
        return repopart.save(participation2);
    }




    @Override
    public List<Participation2> getParticipation() {
        return repopart.findAll();
    }


}
