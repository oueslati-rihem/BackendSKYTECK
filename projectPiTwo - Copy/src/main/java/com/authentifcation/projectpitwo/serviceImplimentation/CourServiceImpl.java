package com.authentifcation.projectpitwo.serviceImplimentation;

import com.authentifcation.projectpitwo.entities.Cours;
import com.authentifcation.projectpitwo.entities.Quiz;
import com.authentifcation.projectpitwo.repository.CoursRepository;
import com.authentifcation.projectpitwo.repository.QuizRepository;
import com.authentifcation.projectpitwo.serviceInterface.IcourService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class CourServiceImpl implements IcourService {
    CoursRepository coursrepo;
    QuizRepository quizrepo;
    @Override
    public List<Cours> retrieveAllCours() {
        return coursrepo.findAll();
    }

    @Override
    public Cours addCours(Cours cours) {
        return coursrepo.save(cours);
    }

    @Override
    public Cours updateCours(Cours cours) {
        return coursrepo.save(cours);
    }

    @Override
    public void deleteCours(Long id) {coursrepo.deleteById(id);
    }

    @Override
    public Cours retrieveCours(Long idC) {
        return coursrepo.findById(idC).orElse(null);
    }
    @Override
    public List<Cours> retrieveAllCoursByUserId(Integer userId) {
        return coursrepo.findByUser_id(userId);
    }
    @Override
    public void affecterQuizAuCours(Long coursId, Long quizId) {
        Cours cours = coursrepo.findById(coursId).orElseThrow(() -> new IllegalArgumentException("Cours non trouvé"));
        Quiz quiz = quizrepo.findById(quizId).orElseThrow(() -> new IllegalArgumentException("Quiz non trouvé"));

        // Initialiser la collection de Quizs si elle est null
        if (cours.getQuizs() == null) {
            cours.setQuizs(new HashSet<>());
        }

        // Initialiser la collection de Questions si elle est null
        if (quiz.getQuestions() == null) {
            quiz.setQuestions(new HashSet<>());
        }

        // Affecter le cours au quiz et vice versa
        cours.getQuizs().add(quiz);
        quiz.setCours(cours);

        // Enregistrer les modifications dans la base de données
        coursrepo.save(cours);
        quizrepo.save(quiz);
    }
    public List<Object[]> getCoursWithQuizCount() {

        return coursrepo.findCoursWithQuizCount();
    }

    public void addRating(Long coursId, int newRating) {
        Cours cours = coursrepo.findByIdC(coursId);
        double sum = cours.getAverageRating() * cours.getTotalRatings();
        int totalRatings = cours.getTotalRatings();
        totalRatings++;
        double averageRating = (sum + newRating) / totalRatings;
        cours.setTotalRatings(totalRatings);
        cours.setAverageRating(averageRating);
        coursrepo.save(cours);
    }

    @Override
    public Cours findByIdC(Long id) {
        return coursrepo.findByIdC(id);
    }

    public List<Cours> getCoursWithAverageRatingAndTotalRatings() {
        List<Object[]> results = coursrepo.findAllWithAverageRatingAndTotalRatings();
        List<Cours> coursList = new ArrayList<>();

        for (Object[] result : results) {
            Cours cours = (Cours) result[0];
            Double averageRating = (Double) result[1];
            int totalRatings= (int) result[2];
            cours.setAverageRating(averageRating);
            cours.setTotalRatings(totalRatings);
            coursList.add(cours);
        }

        return coursList;
    }

}