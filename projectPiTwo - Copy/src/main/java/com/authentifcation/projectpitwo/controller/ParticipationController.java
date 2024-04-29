package com.authentifcation.projectpitwo.controller;


import com.authentifcation.projectpitwo.entities.Participation;
import com.authentifcation.projectpitwo.repository.ParticipationRepo;
import com.authentifcation.projectpitwo.serviceImplimentation.MailService;
import com.authentifcation.projectpitwo.serviceImplimentation.ParticipationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/ParticipationController")
public class ParticipationController {
    ParticipationService participationService;
    ParticipationRepo participationRepo;
    MailService mailService;


@GetMapping("/GetAllParticipation")
    public List<Participation> GetAllParticipation() {
        return participationService.GetAllParticipation();
    }

@PostMapping("/AddParticipation")
    public Participation AddParticipation( Participation participation) {
        return participationService.AddParticipation(participation);
    }
@PostMapping("/participerOffre/{offreId}")
    public Participation participerOffre(@PathVariable("offreId") Long offreId) {
        return participationService.participerOffre(offreId);
    }
@PutMapping("/AccepterParticipation/{participationId}")
    public String AccepterParticipation(@PathVariable("participationId") Long participationId) {
    Participation participation = participationRepo.findById(participationId).orElse(null);
    if (participation.getStatut().equals("EnAttente")) {
        participationService.AccepterParticipation(participationId);
        mailService.EnvoyerEmail(participation);
        return "Offer accepted successfully.";
    } else {
        throw new IllegalArgumentException("Impossible d'accepter une participation déjà rejetée.");
    }
}
@PutMapping("/RefuserParticipation/{participationId}")
    public String RefuserParticipation(@PathVariable("participationId") Long participationId) {

        Participation participation = participationRepo.findById(participationId).orElse(null);
        if (participation.getStatut().equals("EnAttente")) {
            participationService.RefuserParticipation(participationId);
            mailService.EnvoyerEmail(participation);
            return "Offer accepted successfully.";
        } else {
            throw new IllegalArgumentException("Impossible d'accepter une participation déjà rejetée.");
        }
    }
    @GetMapping("/statistiques")
    public Map<String, Long> calculerStatistiquesParticipationsParOffre() {
        return participationService.calculerStatistiquesParticipationsParOffre();
    }
}

