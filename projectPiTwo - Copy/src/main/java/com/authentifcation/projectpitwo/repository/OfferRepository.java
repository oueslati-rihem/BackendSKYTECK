package com.authentifcation.projectpitwo.repository;

import com.authentifcation.projectpitwo.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {
    Optional<Offer> findByOfferId(Long offerId);
   // List<Offer> findByStatut(String statut);

    @Query("SELECT o FROM Offer o JOIN FETCH o.tuteur u WHERE o.statut = :statut")
    List<Offer> findByStatut(@Param("statut") String statut);


}
