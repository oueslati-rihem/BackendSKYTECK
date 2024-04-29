package com.authentifcation.projectpitwo.serviceInterface;




import com.authentifcation.projectpitwo.entities.Tentative;

import java.util.List;


public interface ITentativeService {
    List<Tentative> getTentativeByUserIdAndQuizId(Integer userId, long quizId);
    Tentative saveTentative(Tentative tentative);
}
