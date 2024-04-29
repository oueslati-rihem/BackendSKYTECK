package com.authentifcation.projectpitwo.serviceInterface;

import com.authentifcation.projectpitwo.entities.Event;
import com.authentifcation.projectpitwo.entities.Participation2;
import jakarta.mail.MessagingException;

import java.util.List;

public interface Participation2Interface {

    void acceptParticipation(Long IdPart) throws MessagingException;
    void rejectParticipation(Long IdPart);
    Participation2 archiveParticipation(Long IdPart);

    List<Participation2> getParticipation();

    Participation2 participate(Integer Id, Long numEvent) ;
    boolean isEventFull(Event event);
    List<Participation2> getParticipationsByUserId(Integer Id);



}
