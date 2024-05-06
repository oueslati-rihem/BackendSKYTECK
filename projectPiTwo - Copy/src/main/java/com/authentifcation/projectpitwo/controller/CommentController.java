package com.authentifcation.projectpitwo.controller;


import com.authentifcation.projectpitwo.entities.Comment;
import com.authentifcation.projectpitwo.serviceInterface.IcommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment management " )
@RestController
@RequestMapping("/comment")
@AllArgsConstructor
public class CommentController {
    IcommentService icommentService ;

    @PostMapping ("/add/{posteId}")
    public Comment addComment(@RequestBody String text, @PathVariable("posteId") Long postId) {
        Comment comment = new Comment();
        comment.setText(text);
        return icommentService.addComment(comment, postId);
    }

    @PutMapping("/update")
    public Comment updateComment(@RequestBody Comment comment) {
        return icommentService.updateComment(comment);
    }

    @GetMapping("/all/{postid}")
    public List<Comment> comments(@PathVariable("postid") Long postid) {
        return icommentService.comments(postid);
    }
    @DeleteMapping("/delete/{commentId}")

    public void removeComment( @PathVariable("commentId") Long commentId) {
        icommentService.removeComment(commentId);
    }
}
