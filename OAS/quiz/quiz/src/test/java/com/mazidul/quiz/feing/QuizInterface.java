package com.mazidul.quiz.feing;

import com.mazidul.quiz.model.QuestionWrapper;
import com.mazidul.quiz.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// Defines a Feign client interface to interact with the "QUESTIONS" microservice
@FeignClient("QUESTIONS")
public interface QuizInterface {

    // Method to fetch a list of question IDs for a quiz based on category and number of questions
    @GetMapping("questions/generate") // HTTP GET request to generate questions
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String categoryName, @RequestParam Integer numQuestions);

    // Method to fetch questions using their IDs
    @PostMapping("questions/getQuestions") // HTTP POST request to get questions based on their IDs
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);

    // Method to calculate the score based on the responses
    @PostMapping("questions/getScore") // HTTP POST request to calculate score
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
