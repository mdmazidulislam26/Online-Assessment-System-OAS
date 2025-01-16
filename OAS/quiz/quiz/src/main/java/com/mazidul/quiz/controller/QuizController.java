package com.mazidul.quiz.controller;

import com.mazidul.quiz.model.QuestionWrapper;
import com.mazidul.quiz.model.QuizDto;
import com.mazidul.quiz.model.Response;
import com.mazidul.quiz.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // This annotation marks the class as a REST controller to handle HTTP requests
@RequestMapping("/quiz") // Defines the base URL for all endpoints in this controller
public class QuizController {

    private final QuizService quizService; // A service instance to interact with business logic

    // Constructor injection for QuizService
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // Endpoint to create a new quiz
    @PostMapping("/create") // POST method to create quiz
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        // Calls the service method to create the quiz with provided details
        return quizService.createQuiz(quizDto.getCategory(), quizDto.getNumQ(), quizDto.getTitle());
    }

    // Endpoint to fetch questions for a specific quiz
    @GetMapping("/get/{id}") // GET method to get quiz questions by quiz ID
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id) {
        // Fetches the list of questions for the given quiz ID
        return quizService.getQuizQuestions(id);
    }

    // Endpoint to submit the answers and calculate the result
    @PostMapping("/submit/{id}") // POST method to submit answers for a quiz
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
        // Calculates the result of the quiz by comparing the responses
        return quizService.calculateResult(id, responses);
    }
}
