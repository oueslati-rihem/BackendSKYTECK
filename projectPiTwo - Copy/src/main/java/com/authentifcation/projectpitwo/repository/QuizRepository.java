package com.authentifcation.projectpitwo.repository;
import org.springframework.data.jpa.repository.Query;

import com.authentifcation.projectpitwo.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
   List<Quiz> findByCoursIdC(Long coursIdC);
   @Query("SELECT q.title, q.moyenneScores FROM Quiz q GROUP BY q.title")
   List<Object[]> findTitleAndAverageScore();


}
