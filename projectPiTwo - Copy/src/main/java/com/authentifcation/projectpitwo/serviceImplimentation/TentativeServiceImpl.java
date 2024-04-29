package com.authentifcation.projectpitwo.serviceImplimentation;

import com.authentifcation.projectpitwo.entities.Tentative;
import com.authentifcation.projectpitwo.repository.TentativeRepository;
import com.authentifcation.projectpitwo.serviceInterface.ITentativeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
public class TentativeServiceImpl implements ITentativeService {
    TentativeRepository tentativeRepository;


    @Override
    public List<Tentative> getTentativeByUserIdAndQuizId(Integer userId, long quizId) {
        return tentativeRepository.findByUserIdAndQuizIdQ(userId,quizId) ;   }

    @Override
    public Tentative saveTentative(Tentative tentative) {

        return tentativeRepository.save(tentative);

    }




}
