package com.authentifcation.projectpitwo.controller;

import com.authentifcation.projectpitwo.entities.Tentative;
import com.authentifcation.projectpitwo.serviceInterface.ITentativeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/tentatives")
@AllArgsConstructor
public class TentativeController {


       ITentativeService tentativeService;


        @PostMapping("/save")
        public ResponseEntity<Tentative> saveTentative(@RequestBody Tentative tentative) {
            Tentative savedTentative = tentativeService.saveTentative(tentative);
            return new ResponseEntity<>(savedTentative, HttpStatus.CREATED);
        }
    @GetMapping("/{userId}/{quizId}")
    public ResponseEntity<List<Tentative>> getTentativesByUserIdAndQuizId(
            @PathVariable Integer userId,
            @PathVariable long quizId) {
        List<Tentative> tentatives = tentativeService.getTentativeByUserIdAndQuizId(userId, quizId);
        if (tentatives.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(tentatives, HttpStatus.OK);
        }
    }



}
