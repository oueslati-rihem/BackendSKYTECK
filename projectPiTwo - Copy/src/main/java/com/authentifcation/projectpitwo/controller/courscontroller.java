package com.authentifcation.projectpitwo.controller;

import com.authentifcation.projectpitwo.entities.Cours;
import com.authentifcation.projectpitwo.serviceInterface.IcourService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@CrossOrigin()
@RestController
@RequestMapping("/cours")
@AllArgsConstructor
public class courscontroller {

    IcourService courService;
    @GetMapping("/retrieveAllCours")
    public List<Cours> retrieveAllCours() {
        return courService.retrieveAllCours();
    }
    @PostMapping("/addCours")
    public Cours addCours(@RequestBody Cours cours) {
        return courService.addCours(cours);
    }
    @PutMapping("/updateCours")
    public Cours updateCours(@RequestBody Cours cours) {
        return courService.updateCours(cours);
    }

    @GetMapping("/getcoursbyid/{numCours}")
    public Cours retrieveCours(@PathVariable("numCours")Long numCours) {
        return courService.retrieveCours(numCours);
    }
    @DeleteMapping("/deleteCours/{id}")
    public void deleteCours(@PathVariable("id") Long id) {
        courService.deleteCours(id);
    }
    @GetMapping("/getCoursByUserId/{userId}")
    public List<Cours> getAllCoursByUserId(@PathVariable Integer userId) {
        return courService.retrieveAllCoursByUserId(userId);
    }

    @PostMapping("/cours/{coursId}/quiz/{quizId}")
    public ResponseEntity<String> affecterQuizAuCours(@PathVariable Long coursId, @PathVariable Long quizId) {
        courService.affecterQuizAuCours(coursId, quizId);
        return ResponseEntity.ok("Quiz affecté au cours avec succès.");
    }

    @GetMapping("/coursWithQuizCount")
    public List<Object[]> getCoursWithQuizCount() {
        return courService.getCoursWithQuizCount();
    }
    @PostMapping("/{coursId}/{rating}")
    public ResponseEntity<String> addRating(@PathVariable Long coursId, @PathVariable int rating) {
        courService.addRating(coursId, rating);
        return ResponseEntity.ok("Rating added successfully");
    }

    @GetMapping("/{coursId}/averageRating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long coursId) {
        Cours cours = courService.findByIdC(coursId);
        return ResponseEntity.ok(cours.getAverageRating());
    }
    @GetMapping("/averageRatingAndTotalRatings")
    public ResponseEntity<List<Cours>> getCoursWithAverageRatingAndTotalRatings() {
        List<Cours> coursList = courService.getCoursWithAverageRatingAndTotalRatings();
        return new ResponseEntity<>(coursList, HttpStatus.OK);
    }
}