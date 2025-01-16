package com.mazidul.questions.controller;

import com.mazidul.questions.model.Question;
import com.mazidul.questions.model.QuestionWrapper;
import com.mazidul.questions.model.Response;
import com.mazidul.questions.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // This annotation marks the class as a REST controller.
@RequestMapping("questions")  // Defines the base URL for all API endpoints in this controller.
public class QuestionController {

    @Autowired  // Injects the QuestionService dependency automatically.
    QuestionService questionService;

    // Endpoint to fetch all questions.
    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestion() {
        // Calls the service method to get all questions.
        return questionService.getAllQuestions();
    }

    // Endpoint to fetch questions by category.
    @GetMapping("findByCategory/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        // Calls the service method to get questions based on category.
        return questionService.getQuestionsByCategory(category);
    }

    // Endpoint to add a new question.
    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        // Calls the service method to add a new question.
        return questionService.addQuestion(question);
    }

    // Endpoint to delete a question by its ID.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id) {
        // Calls the service method to delete a question by its ID.
        return questionService.deleteQuestion(id);
    }

    // Endpoint to fetch questions for a quiz based on category and number of questions.
    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String categoryName, @RequestParam Integer numQuestions) {
        // Calls the service method to get quiz questions based on category and number.
        return questionService.getQuestionsForQuiz(categoryName, numQuestions);
    }

    // Endpoint to fetch questions by their IDs.
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds) {
        // Calls the service method to fetch questions based on their IDs.
        return questionService.getQuestionsFromId(questionIds);
    }

    // Endpoint to calculate score based on responses.
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        // Calls the service method to calculate the score based on responses.
        return questionService.getScore(responses);
    }
}
