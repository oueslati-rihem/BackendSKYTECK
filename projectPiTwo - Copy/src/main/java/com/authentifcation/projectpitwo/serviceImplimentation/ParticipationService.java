package com.authentifcation.projectpitwo.serviceImplimentation;


import com.authentifcation.projectpitwo.entities.Offer;
import com.authentifcation.projectpitwo.entities.Participation;
import com.authentifcation.projectpitwo.repository.OfferRepository;
import com.authentifcation.projectpitwo.repository.ParticipationRepo;
import com.authentifcation.projectpitwo.serviceInterface.ParticipationInterface;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ParticipationService implements ParticipationInterface {
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    ParticipationRepo participationRepo;
    @Override
    public Participation participerOffre(Long offreId) {
        // Logique pour vérifier et enregistrer la participation de l'étudiant à l'offre
        Offer offer = offerRepository.findById(offreId).orElse(null);
        // Vérifier si l'offre existe
        if (offer != null) {
            int nombreParticipations = participationRepo.countByOffer(offer);
            if (nombreParticipations < 3) {
            // Créer une nouvelle participation
            Participation participation = new Participation();

            // Remplir les attributs titre et description avec les valeurs de l'offre
            participation.setTitreoffer(offer.getTitre());
            participation.setDescriptionoffer(offer.getDescription());

            // Définir l'offre associée à la participation
            participation.setOffer(offer);

            // Définir le statut de la participation
            participation.setStatut("EnAttente");

            // Enregistrer la participation dans la base de données
            return participationRepo.save(participation);

            } else {
                // Si le nombre maximal de participations est atteint, renvoyer une erreur
                throw new IllegalArgumentException("Le nombre maximal de participations est déjà atteint pour cette offre.");
            }
        } else {
            throw new IllegalArgumentException("L'offre spécifiée n'existe pas.");
        }


    }

    @Override
    public List<Participation> GetAllParticipation() {
        return participationRepo.findAll();
    }

    @Override
    public Participation AddParticipation(Participation participation) {

        return participationRepo.save(participation);
    }

    @Override
    public String sendmailtofinich(Participation participation) {
        return  "success";

    }

    @Override
    public void AccepterParticipation(Long participationId) {
        Participation participation = participationRepo.findById(participationId).orElseThrow(() -> new IllegalArgumentException("Invalid participation Id"));

        participation.setStatut("Acceptee"); // Marquer l'offre comme acceptée
        participationRepo.save(participation); // Enregistrer la mise à jour de l'offre


    }

    @Override
    public void RefuserParticipation(Long participationId) {
        Participation participation = participationRepo.findById(participationId).orElseThrow(() -> new IllegalArgumentException("Invalid participation Id"));
        participation.setStatut("Rejected"); // Mark the participation as rejected
        participationRepo.save(participation); // Save the updated participation
    }

    @Override
    public Map<String, Long> calculerStatistiquesParticipationsParOffre() {
            List<Object[]> résultats = participationRepo.countParticipationsByOffer();
            Map<String, Long> statistiques = new HashMap<>();

            for (Object[] résultat : résultats) {
                String titreEvenement = (String) résultat[0];
                Long nombreParticipations = (Long) résultat[1];
                statistiques.put(titreEvenement, nombreParticipations);
            }

            return statistiques;
        }
    }



