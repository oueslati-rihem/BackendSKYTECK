package com.authentifcation.projectpitwo.serviceInterface;

import com.authentifcation.projectpitwo.entities.Event;
import com.authentifcation.projectpitwo.entities.Participationevent;
import jakarta.mail.MessagingException;

import java.util.List;

public interface Participation2Interface {

    void acceptParticipation(Long IdPart) throws MessagingException;
    void rejectParticipation(Long IdPart);
    Participationevent archiveParticipation(Long IdPart);

    List<Participationevent> getParticipation();

    Participationevent participate(Integer Id, Long numEvent) ;
    boolean isEventFull(Event event);
    List<Participationevent> getParticipationsByUserId(Integer Id);



}
