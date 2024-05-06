package com.authentifcation.projectpitwo.serviceImplimentation;


import com.authentifcation.projectpitwo.entities.Poste;
import com.authentifcation.projectpitwo.entities.Room;
import com.authentifcation.projectpitwo.repository.PosteRepository;
import com.authentifcation.projectpitwo.repository.RoomRepository;
import com.authentifcation.projectpitwo.serviceInterface.IPosteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class PosteServiceImp implements IPosteService {
    PosteRepository posteRepository ;
    RoomRepository roomRepository ;
    @Override   //save poste in the room
    public Poste addPoste(Poste poste , Long roomId) {
        // Find the room by ID
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + roomId));



        // Associate the post with the room
        poste.setRoom(room);

        // Save the post
        return posteRepository.save(poste);

   }

    @Override
    public Poste updatePoste(Poste poste) {
        return posteRepository.save(poste);
    }

    @Override
    public List<Poste> postesBYRoom(Long roomId) {
        Room room= roomRepository.findById(roomId).orElse(null);
        List<Poste> postes = posteRepository.findAllByRoom(room) ;

    /*    for ( Poste poste :postes){
            poste.setRoom(null);

        }*/
        return  postes ;
    }

    @Override
    public void removePoste(Long numPoste) {
        posteRepository.deleteById(numPoste);

    }

    @Override
    public Integer reactionsList(Long postId) {
/*
        List<Integer> reactions = new ArrayList<>(List.of(0, 0, 0, 0));
*/
           return    0 ;
    }
}
