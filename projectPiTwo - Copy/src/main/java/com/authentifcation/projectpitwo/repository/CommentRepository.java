package com.authentifcation.projectpitwo.repository;

import com.authentifcation.projectpitwo.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPostePostId(Long postId);

    //findbyUser and post
}
