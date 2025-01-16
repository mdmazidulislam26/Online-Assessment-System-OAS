package com.mazidul.questions.service;

import com.mazidul.questions.dao.QuestionDao;
import com.mazidul.questions.model.Question;
import com.mazidul.questions.model.QuestionWrapper;
import com.mazidul.questions.model.Response;
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

public class QuestionServiceTest {

    @Mock
    private QuestionDao questionDao; // Mock for QuestionDao

    @InjectMocks
    private QuestionService questionService; // Service under test

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    // Test for getAllQuestions
    @Test
    public void testGetAllQuestions_Success() {
        // Mocking database response
        List<Question> mockQuestions = Arrays.asList(
                new Question(1, "What is Java?", "Language", "Coffee", "Game", "Platform", "Language","Easy","Genaral"),
                new Question(2, "What is 2+2?", "3", "4", "5", "6", "4","Easy","Genaral")
        );
        Mockito.when(questionDao.findAll()).thenReturn(mockQuestions);

        // Execute service method
        ResponseEntity<List<Question>> response = questionService.getAllQuestions();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetAllQuestions_NoContent() {
        // Mocking empty database response
        Mockito.when(questionDao.findAll()).thenReturn(List.of());

        // Execute service method
        ResponseEntity<List<Question>> response = questionService.getAllQuestions();

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    // Test for getQuestionsByCategory
    @Test
    public void testGetQuestionsByCategory_Success() {
        // Mocking database response
        List<Question> mockQuestions = Arrays.asList(
                new Question(1, "What is Java?", "Language", "Coffee", "Game", "Platform", "Language","Easy","Genaral")
        );
        Mockito.when(questionDao.findByCategory(eq("Technology"))).thenReturn(mockQuestions);

        // Execute service method
        ResponseEntity<List<Question>> response = questionService.getQuestionsByCategory("Technology");

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetQuestionsByCategory_NoContent() {
        // Mocking empty database response
        Mockito.when(questionDao.findByCategory(eq("Technology"))).thenReturn(List.of());

        // Execute service method
        ResponseEntity<List<Question>> response = questionService.getQuestionsByCategory("Technology");

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    // Test for addQuestion
    @Test
    public void testAddQuestion_Success() {
        // Mocking database save operation
        Question question = new Question(1, "What is Java?", "Language", "Coffee", "Game", "Platform", "Language", "Easy", "General");
        Mockito.when(questionDao.save(any(Question.class))).thenReturn(question);

        // Execute service method
        ResponseEntity<String> response = questionService.addQuestion(question);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Question added successfully", response.getBody());
    }


    @Test
    public void testAddQuestion_InvalidData() {
        // Execute service method with invalid data
        ResponseEntity<String> response = questionService.addQuestion(new Question());

        // Assertions
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid question data", response.getBody());
    }

    // Test for deleteQuestion
    @Test
    public void testDeleteQuestion_Success() {
        // Mocking database response
        Mockito.when(questionDao.findById(eq(1))).thenReturn(Optional.of(new Question()));

        // Mocking database delete operation
        Mockito.doNothing().when(questionDao).deleteById(eq(1));

        // Execute service method
        ResponseEntity<String> response = questionService.deleteQuestion(1);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Question deleted successfully", response.getBody());
    }

    @Test
    public void testDeleteQuestion_NotFound() {
        // Mocking database response with empty result
        Mockito.when(questionDao.findById(eq(1))).thenReturn(Optional.empty());

        // Execute service method
        ResponseEntity<String> response = questionService.deleteQuestion(1);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Question not found with id: 1", response.getBody());
    }

    // Test for getQuestionsForQuiz
    @Test
    public void testGetQuestionsForQuiz_Success() {
        // Mocking database response
        List<Integer> mockQuestionIds = Arrays.asList(1, 2, 3);
        Mockito.when(questionDao.findRandomQuestionsByCategory(eq("Science"), eq(3))).thenReturn(mockQuestionIds);

        // Execute service method
        ResponseEntity<List<Integer>> response = questionService.getQuestionsForQuiz("Science", 3);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }

    @Test
    public void testGetQuestionsForQuiz_NoContent() {
        // Mocking empty database response
        Mockito.when(questionDao.findRandomQuestionsByCategory(eq("Science"), eq(3))).thenReturn(List.of());

        // Execute service method
        ResponseEntity<List<Integer>> response = questionService.getQuestionsForQuiz("Science", 3);

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    // Test for getScore
    @Test
    public void testGetScore_Success() {
        // Mocking database response
        Mockito.when(questionDao.findById(eq(1))).thenReturn(Optional.of(
                new Question(1, "What is Java?", "Language", "Coffee", "Game", "Platform", "Language","Easy","Genaral")
        ));

        // Sample responses
        List<Response> responses = List.of(new Response(1, "Language"));

        // Execute service method
        ResponseEntity<Integer> response = questionService.getScore(responses);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody());
    }

    @Test
    public void testGetScore_BadRequest() {
        // Execute service method with invalid input
        ResponseEntity<Integer> response = questionService.getScore(null);

        // Assertions
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0, response.getBody());
    }
}
