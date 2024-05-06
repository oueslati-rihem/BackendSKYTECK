package com.authentifcation.projectpitwo.repository;


import com.authentifcation.projectpitwo.entities.Poste;
import com.authentifcation.projectpitwo.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosteRepository extends JpaRepository<Poste,Long> {

    List<Poste> findAllByRoom(Room room);
    List<Poste>findByPostName(String postName);
  /*  @Query("SELECT COUNT(r) FROM Poste p JOIN p.reactions r WHERE p.postId = :postId AND KEY(r) = 'LIKE'")
    Integer countLikeReactionsByPostId(@Param("postId") Long postId);  //findByUser*/
}
