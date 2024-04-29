package com.authentifcation.projectpitwo.repository;

import com.authentifcation.projectpitwo.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    List<Question> findByQuizIdQ(Long quizIdQ);
    void deleteByQuizIdQ(Long quizIdQ); }

