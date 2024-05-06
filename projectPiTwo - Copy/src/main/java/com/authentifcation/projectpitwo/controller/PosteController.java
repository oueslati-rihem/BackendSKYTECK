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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
@Tag(name = "Poste management " )
@RestController
@RequestMapping("/poste")
@AllArgsConstructor
public class PosteController {
    IPosteService IposteService ;
    PosteRepository posteRepository;
    public static  String uploadDirectory1 =
            System.getProperty("user.dir")+"/src/main/resources/uploads" ;

    @GetMapping("/searchpost/{postName}")
    public List<Poste> searchPoste(@PathVariable("postName") String postName){
        return  posteRepository.findByPostName(postName);




    }



    @GetMapping("/add-reaction/{reactiontype}/{postid}")
    public Integer addReaction(@PathVariable("reactiontype") Reaction reactiontype , @PathVariable("postid")Long postid){
        int count=0;
        Poste poste= posteRepository.findById(postid).orElse(null);

        if (poste.getReactions().containsKey(reactiontype)) {
             count = poste.getReactions().get(reactiontype);
             count++;
        }
        else {
            poste.getReactions().put(reactiontype, 1);
            count=1;
        }
        poste.getReactions().put(reactiontype, count );
         posteRepository.save(poste);
        return count ;
    }
    @GetMapping("/remove-reaction/{reactiontype}/{postid}")
    public Integer removeReaction(@PathVariable("reactiontype")Reaction reactiontype ,@PathVariable("postid")Long postid){
        int count=0;
        Poste poste= posteRepository.findById(postid).orElse(null);

        if (poste.getReactions().containsKey(reactiontype)) {
            count = poste.getReactions().get(reactiontype);
            count--;
        }
        poste.getReactions().put(reactiontype, count );
        posteRepository.save(poste);
        return count ;
    }

    @GetMapping("/reactionLike/{reactiontype}/{postid}")
    public  Integer getReaction(@PathVariable("reactiontype")Reaction reactiontype ,@PathVariable  ("postid")Long postid ){
        int countlikes=0;
        int countdislikes=0;
        Poste poste= posteRepository.findById(postid).orElse(null);
        if (poste.getReactions().containsKey(Reaction.LIKE)) {
            countlikes = poste.getReactions().get(Reaction.LIKE);
            countlikes++;
            poste.getReactions().put(Reaction.LIKE, countlikes );
            posteRepository.save(poste);
        }
        if (poste.getReactions().containsKey(Reaction.HATE)) {
            countdislikes = poste.getReactions().get(Reaction.HATE);
            countdislikes++;
            poste.getReactions().put(Reaction.HATE, countdislikes );
            posteRepository.save(poste);
        }
        return null ;
    }
@PostMapping("/add/{roomid}")
public Poste addPoste(Poste poste,
                      @RequestParam("imageURL") MultipartFile file
                    ,@PathVariable  ("roomid")Long roomid )  throws IOException {
    System.out.println(uploadDirectory1);
        System.out.println(poste.getPostName());
    String originalFilename= file.getOriginalFilename();
    Path fileNameAndPath = Paths.get(uploadDirectory1,originalFilename) ;
    Files.write(fileNameAndPath,file.getBytes());
    poste.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
   return IposteService.addPoste(poste,roomid);
    }

  /*  @PostMapping("/room/{roomId}/post")
    public Poste createPostInRoom(@PathVariable Long roomId, @ModelAttribute Poste postRequest) {
       return  IposteService.addPoste(postRequest,roomId);
    }*/
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
