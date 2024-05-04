package com.authentifcation.projectpitwo.serviceImplimentation;

import com.authentifcation.projectpitwo.entities.Question;
import com.authentifcation.projectpitwo.entities.Quiz;
import com.authentifcation.projectpitwo.entities.Tentative;
import com.authentifcation.projectpitwo.repository.QuestionRepository;
import com.authentifcation.projectpitwo.repository.QuizRepository;
import com.authentifcation.projectpitwo.repository.TentativeRepository;
import com.authentifcation.projectpitwo.serviceInterface.IQuizService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements IQuizService {
    QuizRepository Quizrepo;
    QuestionRepository QuestionRepo;
    TentativeRepository tentativeRepository;

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

    @Override
    public List<Quiz> calculateQuizAverages() {
        List<Quiz> quizzes = Quizrepo.findAll();
        Map<Long, List<Long>> quizScoresMap = new HashMap<>();

        // Récupérer les scores pour chaque quiz
        for (Quiz quiz : quizzes) {
            List<Tentative> tentatives = tentativeRepository.findByQuiz(quiz);
            List<Long> scores = tentatives.stream()
                    .map(Tentative::getNote)
                    .collect(Collectors.toList());
            quizScoresMap.put(quiz.getIdQ(), scores);
        }

        // Calculer la moyenne des scores pour chaque quiz
        for (Quiz quiz : quizzes) {
            List<Long> scores = quizScoresMap.get(quiz.getIdQ());
            double averageScore = calculateAverage(scores);
            quiz.setMoyenneScores(averageScore);
        }

        return quizzes;
    }

    // Méthode utilitaire pour calculer la moyenne d'une liste de nombres
    private double calculateAverage(List<Long> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0.0;
        }
        long sum = 0;
        for (long number : numbers) {
            sum += number;
        }
        return (double) sum / numbers.size();
    }
}