package com.mazidul.quiz.service;

import com.mazidul.quiz.dao.QuizDao;
import com.mazidul.quiz.feing.QuizInterface;
import com.mazidul.quiz.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marks this class as a service component in Spring
public class QuizService {

    @Autowired
    QuizDao quizDao; // Dependency injection for the QuizDao, used to interact with the database

    @Autowired
    QuizInterface quizInterface; // Dependency injection for QuizInterface, used to communicate with external APIs

    // Method to create a new quiz
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        // Validate input parameters
        if (category == null || category.isEmpty() || numQ <= 0 || title == null || title.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid input data");
        }

        // Fetch questions for the quiz using the QuizInterface
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        if (questions == null || questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No questions found for the given category");
        }

        // Create a new Quiz object and set its properties
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        // Save the quiz to the database
        quizDao.save(quiz);
        return ResponseEntity.ok("Quiz created successfully");
    }

    // Method to get the questions for a specific quiz by its ID
    @Transactional // Ensures the method runs within a transaction context
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        // Validate the quiz ID
        if (id == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Fetch the quiz from the database by its ID
        Optional<Quiz> quizOptional = quizDao.findById(id);
        if (quizOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        // Get the list of question IDs from the quiz
        Quiz quiz = quizOptional.get();
        List<Integer> questionIds = quiz.getQuestions();

        // Fetch the question details using the QuizInterface
        ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds);
        if (questions == null || questions.getBody() == null || questions.getBody().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        // Return the list of questions
        return ResponseEntity.ok(questions.getBody());
    }

    // Method to calculate the score based on the responses submitted by the user
    @Transactional // Ensures the method runs within a transaction context
    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        // Validate the quiz ID and responses
        if (id == null || responses == null || responses.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Delegate the score calculation to the QuizInterface
        return quizInterface.getScore(responses);
    }
}
