package com.authentifcation.projectpitwo.serviceImplimentation;

import com.authentifcation.projectpitwo.entities.Cours;
import com.authentifcation.projectpitwo.entities.Quiz;
import com.authentifcation.projectpitwo.repository.CoursRepository;
import com.authentifcation.projectpitwo.repository.QuizRepository;
import com.authentifcation.projectpitwo.serviceInterface.IcourService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


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
}