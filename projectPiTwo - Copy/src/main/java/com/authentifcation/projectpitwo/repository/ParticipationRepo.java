package com.authentifcation.projectpitwo.repository;


import com.authentifcation.projectpitwo.entities.Offer;
import com.authentifcation.projectpitwo.entities.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepo extends JpaRepository<Participation,Long> {
    int countByOffer(Offer offer);
    @Query("SELECT p.titreoffer, COUNT(p) FROM Participation p GROUP BY p.titreoffer")
    List<Object[]> countParticipationsByOffer();
}
