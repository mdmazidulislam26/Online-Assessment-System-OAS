package com.mazidul.quiz.service;

import com.mazidul.quiz.dao.QuizDao;
import com.mazidul.quiz.feing.QuizInterface;
import com.mazidul.quiz.model.QuestionWrapper;
import com.mazidul.quiz.model.Quiz;
import com.mazidul.quiz.model.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class QuizServiceTest {

    @Mock
    private QuizDao quizDao; // Mock for QuizDao

    @Mock
    private QuizInterface quizInterface; // Mock for QuizInterface

    @InjectMocks
    private QuizService quizService; // Service under test

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    // Test for createQuiz
    @Test
    public void testCreateQuiz_Success() {
        // Mocking external API response
        Mockito.when(quizInterface.getQuestionsForQuiz(eq("Science"), eq(5)))
                .thenReturn(ResponseEntity.ok(Arrays.asList(1, 2, 3, 4, 5)));

        // Mocking database save operation (assuming save returns an object or null)
        Mockito.when(quizDao.save(any(Quiz.class))).thenReturn(null);

        // Execute service method
        ResponseEntity<String> response = quizService.createQuiz("Science", 5, "General Science");

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Quiz created successfully", response.getBody());
    }




    @Test
    public void testCreateQuiz_InvalidInput() {
        // Execute service method with invalid input
        ResponseEntity<String> response = quizService.createQuiz("", 0, "");

        // Assertions
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid input data", response.getBody());
    }

    @Test
    public void testCreateQuiz_NoQuestionsFound() {
        // Mocking external API response with no questions
        Mockito.when(quizInterface.getQuestionsForQuiz(eq("Science"), eq(5)))
                .thenReturn(ResponseEntity.ok(List.of()));

        // Execute service method
        ResponseEntity<String> response = quizService.createQuiz("Science", 5, "General Science");

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("No questions found for the given category", response.getBody());
    }

    // Test for getQuizQuestions
    @Test
    public void testGetQuizQuestions_Success() {
        // Mocking database response
        Quiz quiz = new Quiz();
        quiz.setId(1);
        quiz.setQuestions(Arrays.asList(1, 2, 3));
        Mockito.when(quizDao.findById(eq(1))).thenReturn(Optional.of(quiz));

        // Mocking external API response
        List<QuestionWrapper> mockQuestions = Arrays.asList(
                new QuestionWrapper(1, "What is Java?", "Language", "Coffee", "Animal", "Game"),
                new QuestionWrapper(2, "What is 2+2?", "3", "4", "5", "6")
        );
        Mockito.when(quizInterface.getQuestionsFromId(eq(Arrays.asList(1, 2, 3))))
                .thenReturn(ResponseEntity.ok(mockQuestions));

        // Execute service method
        ResponseEntity<List<QuestionWrapper>> response = quizService.getQuizQuestions(1);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetQuizQuestions_NoQuizFound() {
        // Mocking database response with empty result
        Mockito.when(quizDao.findById(eq(1))).thenReturn(Optional.empty());

        // Execute service method
        ResponseEntity<List<QuestionWrapper>> response = quizService.getQuizQuestions(1);

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetQuizQuestions_NoQuestionsFound() {
        // Mocking database response
        Quiz quiz = new Quiz();
        quiz.setId(1);
        quiz.setQuestions(Arrays.asList(1, 2, 3));
        Mockito.when(quizDao.findById(eq(1))).thenReturn(Optional.of(quiz));

        // Mocking external API response with no questions
        Mockito.when(quizInterface.getQuestionsFromId(eq(Arrays.asList(1, 2, 3))))
                .thenReturn(ResponseEntity.ok(List.of()));

        // Execute service method
        ResponseEntity<List<QuestionWrapper>> response = quizService.getQuizQuestions(1);

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    // Test for calculateResult
    @Test
    public void testCalculateResult_Success() {
        // Mocking external API response
        Mockito.when(quizInterface.getScore(any())).thenReturn(ResponseEntity.ok(3));

        // Sample responses
        List<Response> responses = Arrays.asList(
                new Response(1, "Language"),
                new Response(2, "4")
        );

        // Execute service method
        ResponseEntity<Integer> response = quizService.calculateResult(1, responses);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody());
    }

    @Test
    public void testCalculateResult_InvalidInput() {
        // Execute service method with invalid input
        ResponseEntity<Integer> response = quizService.calculateResult(null, null);

        // Assertions
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
