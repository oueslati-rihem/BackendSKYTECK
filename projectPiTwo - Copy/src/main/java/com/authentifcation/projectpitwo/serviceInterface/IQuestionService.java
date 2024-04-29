package com.authentifcation.projectpitwo.serviceInterface;



import com.authentifcation.projectpitwo.entities.Question;

import java.util.List;

public interface IQuestionService {
    Question addQuestion(Question Question);

    void deleteQuestion(Long id) ;

    List<Question> retrieveAllQuestionByQuizID(Long QuizId);
    void deleteQuestionsByQuizId(Long QuizId);
}
