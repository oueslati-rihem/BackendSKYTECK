package com.authentifcation.projectpitwo.controller;


import com.authentifcation.projectpitwo.entities.Offer;
import com.authentifcation.projectpitwo.entities.User;
import com.authentifcation.projectpitwo.repository.OfferRepository;
import com.authentifcation.projectpitwo.service.UserService;
import com.authentifcation.projectpitwo.serviceImplimentation.CategoryService;
import com.authentifcation.projectpitwo.serviceImplimentation.MailService;
import com.authentifcation.projectpitwo.serviceImplimentation.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/OfferController")
@CrossOrigin

public class OfferController {
    OfferService offerService;
    UserService userService;
    CategoryService categoryService;
    OfferRepository offerRepository;
    MailService mailService;






    @GetMapping("/AllOffer")
    public List<Offer> AllOffer() {

        return offerService.AllOffer();
    }









    @DeleteMapping("/DeleteOffer/{offerId}")
    public String DeleteOffer(@PathVariable("offerId")Long offerId) {

        return offerService.DeleteOffer(offerId);
    }


    @PutMapping("/updateOffer")
    Offer modifierOffre(@RequestBody Offer offer) {
        return offerService.updateOffer(offer);
    }

@GetMapping("/getByofferId/{offerId}")
    public Optional<Offer> getByofferId(@PathVariable Long offerId) {
        return offerService.getByofferId(offerId);
    }

@PutMapping("/AccepterOffer/{offerId}")
    public String AccepterOffer(@PathVariable Long offerId) {
    Offer offer = offerRepository.findById(offerId).orElse(null);
    if (offer.getStatut().equals("Encours")) {
        offerService.AccepterOffer(offerId);

         return "Offer accepted successfully.";
    } else {
        throw new IllegalArgumentException("Impossible d'accepter une offre déjà rejetée.");
    }
    }
@PutMapping("/RefuserOffer/{offerId}")
    public String RefuserOffer(@PathVariable Long offerId) {
    Offer offer = offerRepository.findById(offerId).orElse(null);
    if (offer.getStatut().equals("Encours")) {
        offerService.RefuserOffer(offerId);
        return "Offer rejected successfully.";
    }else {
        throw new IllegalArgumentException("Impossible d'accepter une offre déjà rejetée.");
}
}
@GetMapping("/getOfferByStatut")
    public List<Offer> getOfferByStatut() {
        return offerService.getOfferByStatut();
    }

    @PostMapping( "/AddOffer/{id}")
    public Offer AddOffer(@RequestBody Offer offer,@PathVariable("id") Integer id) {
        User user = userService.getUserById(id);
        offer.setTuteur(user);
        return offerService.AddOffer(offer);
    }
}
/*
    @PostMapping( "/AddOffer/{userId}")
    public Offer AddOffer(@RequestBody Offer offer,@PathVariable("userId") Long userId) {
        // Trouver l'utilisateur dans la base de données
        User user = userService.getUserById(userId);

        // Affecter l'utilisateur à l'offre
        offer.setTuteur(user);
        return offerService.AddOffer(offer);
    }*/
