package com.authentifcation.projectpitwo.serviceInterface;



import com.authentifcation.projectpitwo.entities.Poste;

import java.util.List;


public interface IPosteService {
    Poste addPoste(Poste poste , Long RoomId); // creating the poste and adding it to a room
    Poste updatePoste(Poste poste); //update a poste within a room
    public List<Poste> postesBYRoom(Long roomId);

    void removePoste(Long numPoste);  //even tho it needs to ba saved in history hmmm
 Integer reactionsList(Long postId);

}
