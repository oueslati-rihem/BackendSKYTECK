package com.authentifcation.projectpitwo.serviceImplimentation;


import com.authentifcation.projectpitwo.entities.Comment;
import com.authentifcation.projectpitwo.entities.Poste;
import com.authentifcation.projectpitwo.repository.CommentRepository;
import com.authentifcation.projectpitwo.repository.PosteRepository;
import com.authentifcation.projectpitwo.serviceInterface.IcommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImp implements IcommentService {
    CommentRepository commentRepository ;
    PosteRepository posteRepository ;
    @Override
    public Comment addComment(Comment comment, Long postId) {
        Poste poste =  posteRepository.findById(postId).orElse(null);
        if ( poste !=null){
            comment.setPoste(poste);
            poste.getComments().add(comment);
           return commentRepository.save(comment);

        }
        else  throw  new RuntimeException (" no existancetance of poste "+postId);
    }

    @Override
    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> comments(Long postId) {
        return commentRepository.findByPostePostId(postId);
    }

    @Override
    public void removeComment(Long commentId) {
        this.commentRepository.deleteById(commentId);

    }
}
