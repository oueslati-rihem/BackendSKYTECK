package com.authentifcation.projectpitwo.serviceImplimentation;


import com.authentifcation.projectpitwo.entities.Offer;
import com.authentifcation.projectpitwo.repository.OfferRepository;
import com.authentifcation.projectpitwo.serviceInterface.OfferInterface;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OfferService implements OfferInterface {
    @Autowired
    OfferRepository offerRepository;



    @Override
    public Offer AddOffer(Offer offer) {

        return offerRepository.save(offer);
    }

    @Override
    public List<Offer> AllOffer() {

        return offerRepository.findAll();
    }

    @Override
    public String DeleteOffer(Long offerId) {
        if (offerRepository.findById(offerId)!=null){
        offerRepository.deleteById(offerId);
        return "Offer supprimé";}
        return "Offer non touvable";
    }

    @Override
    public Offer updateOffer(Offer offer) {
        if (offerRepository.existsById(offer.getOfferId())){
            return offerRepository.save(offer);
        }
        return null;
    }

    @Override
    public Optional<Offer> getByofferId(Long offerId) {
        return offerRepository.findByOfferId(offerId);
    }

    @Override
    public void AccepterOffer(Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new IllegalArgumentException("Invalid participation Id"));

            offer.setStatut("Acceptee"); // Marquer l'offre comme acceptée
            offerRepository.save(offer); // Enregistrer la mise à jour de l'offre


    }

    @Override
    public void RefuserOffer(Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new IllegalArgumentException("Invalid participation Id"));
        offer.setStatut("Rejected"); // Mark the participation as rejected
        offerRepository.save(offer); // Save the updated participation
    }

    @Override
    public List<Offer> getOfferByStatut() {
        return offerRepository.findByStatut("Acceptee");
    }


}
