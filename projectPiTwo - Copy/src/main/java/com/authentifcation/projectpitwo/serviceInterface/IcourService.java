package com.authentifcation.projectpitwo.serviceInterface;



import com.authentifcation.projectpitwo.entities.Cours;

import java.util.List;

public interface IcourService {
    List<Cours> retrieveAllCours();
    Cours addCours(Cours cours);
    Cours updateCours (Cours cour);

    void deleteCours(Long id);
     Cours retrieveCours(Long idC);
    List<Cours> retrieveAllCoursByUserId(Integer userId);
    public void affecterQuizAuCours(Long coursId, Long quizId);
    List<Object[]> getCoursWithQuizCount();

}
