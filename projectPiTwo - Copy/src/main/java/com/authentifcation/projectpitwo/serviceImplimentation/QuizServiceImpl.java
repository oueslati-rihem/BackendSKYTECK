package com.authentifcation.projectpitwo.serviceImplimentation;

import com.authentifcation.projectpitwo.entities.Question;
import com.authentifcation.projectpitwo.entities.Quiz;
import com.authentifcation.projectpitwo.repository.QuestionRepository;
import com.authentifcation.projectpitwo.repository.QuizRepository;
import com.authentifcation.projectpitwo.serviceInterface.IQuizService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
@AllArgsConstructor
public class QuizServiceImpl implements IQuizService {
    QuizRepository Quizrepo;
    QuestionRepository QuestionRepo;

    @Override
    public Quiz addQuiz(Quiz Quiz) {
        return Quizrepo.save(Quiz);
    }

    @Override
    public Quiz updateQuiz(Quiz Quiz) {
        return Quizrepo.save(Quiz) ;
    }

    @Override
    public void deleteQuiz(Long id) {
        Quizrepo.deleteById(id);
    }

    @Override
    public List<Quiz> retrieveAllQuizByCourID(Long CourId) {
        return Quizrepo.findByCoursIdC(CourId);
    }

    @Override
    public void addQuestionToQuiz(Long quizId, long questionId) {
        Quiz quiz = Quizrepo.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found with id: " + quizId));

        Question question = QuestionRepo.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found with id: " + questionId));

        question.setQuiz(quiz);
        QuestionRepo.save(question);
    }
}