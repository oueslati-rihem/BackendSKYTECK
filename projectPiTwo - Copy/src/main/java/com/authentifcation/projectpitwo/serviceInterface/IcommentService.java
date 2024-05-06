package com.authentifcation.projectpitwo.serviceInterface;



import com.authentifcation.projectpitwo.entities.Comment;

import java.util.List;

public interface IcommentService {

    Comment addComment(Comment comment, Long postId); // Creating a comment and adding it to a post

    Comment updateComment(Comment comment); // Update a comment within a post

    public List<Comment> comments(Long postId);
    void removeComment(Long commentId); // Remove a comment

}
