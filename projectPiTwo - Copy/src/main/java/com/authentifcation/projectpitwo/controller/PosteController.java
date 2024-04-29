package com.authentifcation.projectpitwo.controller;


import com.authentifcation.projectpitwo.entities.Poste;
import com.authentifcation.projectpitwo.entities.Reaction;
import com.authentifcation.projectpitwo.repository.PosteRepository;
import com.authentifcation.projectpitwo.serviceInterface.IPosteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
@Tag(name = "Poste management " )
@RestController
@RequestMapping("/poste")
@AllArgsConstructor
@CrossOrigin()

public class PosteController {
    IPosteService IposteService ;
    PosteRepository posteRepository;
    public static  String uploadDirectory1 =
            System.getProperty("user.dir")+"/src/main/resources/uploads" ;
    @PostMapping("/add-reaction/{reactiontype}/{postid}")
    public Poste addReaction(@PathVariable("reactiontype") Reaction reactiontype , @PathVariable("postid")Long postid){
        int count=0;
        Poste poste= posteRepository.findById(postid).orElse(null);

        if (poste.getReactions().containsKey(reactiontype)) {
             count = poste.getReactions().get(reactiontype);
        }
        poste.getReactions().put(reactiontype, count + 1);
        return posteRepository.save(poste);
    }
@PostMapping("/add/{roomId}")
public Poste addPoste(@ModelAttribute Poste poste  , @PathVariable("roomId") Long RoomId, @RequestParam("image") MultipartFile file)  throws IOException {
        System.out.println(poste.getPostName());

    poste.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
         IposteService.addPoste(poste, RoomId);
        return poste ;
    }

    @PostMapping("/room/{roomId}/post")
    public Poste createPostInRoom(@PathVariable Long roomId, @ModelAttribute Poste postRequest) {
       return  IposteService.addPoste(postRequest,roomId);
    }
@PutMapping("/update")
    public Poste updatePoste(Poste poste) {
        return IposteService.updatePoste(poste);
    }

    @GetMapping("/roomid/{roomid}")
    public List<Poste> postes(@PathVariable("roomid") Long roomid) {
        System.out.println("hhhh4"+roomid);
        return IposteService.postesBYRoom(roomid);
    }

    @DeleteMapping("/delete/{numPoste}")
    public void removePoste(@PathVariable("numPoste") Long numPoste) {
        IposteService.removePoste( numPoste);
    }
}
