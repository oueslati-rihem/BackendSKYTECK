package com.authentifcation.projectpitwo.controller;

import com.authentifcation.projectpitwo.entities.Question;
import com.authentifcation.projectpitwo.serviceInterface.IQuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@CrossOrigin()
@RestController
@RequestMapping("/question")
@AllArgsConstructor
public class QuestionController {
    IQuestionService questionService;
    @PostMapping("/addquestion")
    public Question addQuestion(@RequestBody Question Question) {
        return questionService.addQuestion(Question);
    }
    @DeleteMapping("/deletequestion/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("id") Long id) {
        questionService.deleteQuestion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/byQuiz/{quizId}")
    public ResponseEntity<List<Question>> getAllQuestionsByQuizId(@PathVariable Long quizId) {
        List<Question> questions = questionService.retrieveAllQuestionByQuizID(quizId);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
    @DeleteMapping("/deleteques/{quizId}")
    public ResponseEntity<Void> deleteQuestionsByQuizId(@PathVariable("quizId") Long QuizId) {
        questionService.deleteQuestionsByQuizId(QuizId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
