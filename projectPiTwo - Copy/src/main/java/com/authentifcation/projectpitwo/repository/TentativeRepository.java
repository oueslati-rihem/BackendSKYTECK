package com.authentifcation.projectpitwo.repository;

import com.authentifcation.projectpitwo.entities.Quiz;
import com.authentifcation.projectpitwo.entities.Tentative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface TentativeRepository extends JpaRepository<Tentative,Long> {
    List<Tentative> findByUserIdAndQuizIdQ(Integer userId, long quizId);

    List<Tentative> findByQuiz(Quiz quiz);
}
