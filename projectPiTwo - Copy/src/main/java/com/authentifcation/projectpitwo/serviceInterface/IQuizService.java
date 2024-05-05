package com.authentifcation.projectpitwo.serviceInterface;



import com.authentifcation.projectpitwo.entities.Quiz;

import java.util.List;

public interface IQuizService {

    Quiz addQuiz(Quiz Quiz);
    Quiz updateQuiz (Quiz Quiz);
    void deleteQuiz(Long id);
    List<Quiz> retrieveAllQuizByCourID(Long CourId);
    void  addQuestionToQuiz(Long quizId, long questionid);
    List<Quiz> calculateQuizAverages();
    List<Object[]> findTitleAndAverageScore();

}
