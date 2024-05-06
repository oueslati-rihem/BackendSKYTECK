package com.authentifcation.projectpitwo.controller;


import com.authentifcation.projectpitwo.entities.Quiz;
import com.authentifcation.projectpitwo.serviceInterface.IQuizService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/quiz")
@AllArgsConstructor
public class QuizController {

    IQuizService quizservice;



    @PostMapping("/addQuiz")
    public Quiz addQuiz(@RequestBody Quiz Quiz) {
        return  quizservice.addQuiz(Quiz);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable("id") Long id) {
        quizservice.deleteQuiz(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/byCourse/{courseId}")
    public ResponseEntity<List<Quiz>> getAllQuizzesByCourseId(@PathVariable Long courseId) {
        List<Quiz> quizzes = quizservice.retrieveAllQuizByCourID(courseId);
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @PostMapping("/{quizId}/questions/{questionId}")
    public ResponseEntity<String> addQuestionToQuiz(@PathVariable Long quizId, @PathVariable Long questionId) {
        try {
            quizservice.addQuestionToQuiz(quizId, questionId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/moyennestatistics")
    public ResponseEntity<?> getQuizStatistics() {
        List<Object[]> quizStatistics = quizservice.findTitleAndAverageScore();
        return ResponseEntity.ok(quizStatistics);
    }
    @GetMapping("/calculateAverages")
    public List<Quiz> calculateQuizAverages() {
        return quizservice.calculateQuizAverages();
    }

}
