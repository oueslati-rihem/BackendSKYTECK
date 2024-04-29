package com.authentifcation.projectpitwo.serviceInterface;


import com.authentifcation.projectpitwo.entities.Participation;

import java.util.List;
import java.util.Map;

public interface ParticipationInterface {
    Participation participerOffre(Long offreId);
    List<Participation> GetAllParticipation();
    Participation AddParticipation(Participation participation);

    void AccepterParticipation(Long participationId);
    void RefuserParticipation(Long participationId);
    Map<String, Long> calculerStatistiquesParticipationsParOffre();
}
