package com.authentifcation.projectpitwo.serviceImplimentation;

import com.authentifcation.projectpitwo.entities.Question;
import com.authentifcation.projectpitwo.repository.QuestionRepository;
import com.authentifcation.projectpitwo.repository.QuizRepository;
import com.authentifcation.projectpitwo.serviceInterface.IQuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
@AllArgsConstructor
public class QuestionServiceImpl implements IQuestionService {
    QuestionRepository QuestionRepo;
    QuizRepository quizRepository;

    @Override
    public Question addQuestion(Question Question) {
        return QuestionRepo.save(Question);
    }

    @Override
    public void deleteQuestion(Long id) {QuestionRepo.deleteById(id);

    }




    @Override
    public List<Question> retrieveAllQuestionByQuizID(Long QuizId) {
        return QuestionRepo.findByQuizIdQ(QuizId);

    }

    @Override
    public void deleteQuestionsByQuizId(Long QuizId) {
        QuestionRepo.deleteByQuizIdQ(QuizId);

    }


}
