package com.authentifcation.projectpitwo.serviceInterface;


import com.authentifcation.projectpitwo.entities.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferInterface {


    Offer AddOffer(Offer offer);
    List<Offer> AllOffer();
    String DeleteOffer(Long offerId);
    Offer updateOffer(Offer offer);

    Optional<Offer> getByofferId(Long offerId);

    void AccepterOffer(Long offerId);
    void RefuserOffer(Long offerId);
    List <Offer> getOfferByStatut();


}
