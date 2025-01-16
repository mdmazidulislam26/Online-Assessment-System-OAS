package com.mazidul.quiz.controller;

import com.mazidul.quiz.model.QuestionWrapper;
import com.mazidul.quiz.service.QuizService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuizController.class) // Marks this as a test for the QuizController
public class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc is used for simulating HTTP requests

    @MockBean
    private QuizService quizService; // Mock the QuizService to isolate controller testing

    // Test for createQuiz endpoint
    @Test
    public void testCreateQuiz() throws Exception {
        // Mocking the service response
        Mockito.when(quizService.createQuiz(
                        Mockito.any(String.class),
                        Mockito.any(Integer.class),
                        Mockito.any(String.class)))
                .thenReturn(ResponseEntity.ok("Quiz created successfully"));

        // Sample request body
        String requestBody = """
        {
            "category": "Science",
            "numQ": 3,
            "title": "General Science"
        }
    """;

        // Simulating a POST request
        mockMvc.perform(post("/quiz/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Quiz created successfully"));
    }


    // Test for getQuizQuestions endpoint
    @Test
    public void testGetQuizQuestions() throws Exception {
        // Mocking the service response
        List<QuestionWrapper> mockQuestions = Arrays.asList(
                new QuestionWrapper(1, "What is Java?", "Language", "Coffee", "Animal", "Game"),
                new QuestionWrapper(2, "What is 2+2?", "3", "4", "5", "6")
        );
        Mockito.when(quizService.getQuizQuestions(eq(1)))
                .thenReturn(ResponseEntity.ok(mockQuestions));

        // Simulating a GET request
        mockMvc.perform(get("/quiz/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].questionTitle").value("What is Java?"))
                .andExpect(jsonPath("$[1].questionTitle").value("What is 2+2?"));
    }

    // Test for submitQuiz endpoint
    @Test
    public void testSubmitQuiz() throws Exception {
        // Mocking the service response
        Mockito.when(quizService.calculateResult(eq(1), any()))
                .thenReturn(ResponseEntity.ok(3));

        // Sample request body
        String requestBody = """
            [
                {"response": "Language"},
                {"response": "4"},
                {"response": "CorrectAnswer"}
            ]
        """;

        // Simulating a POST request
        mockMvc.perform(post("/quiz/submit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }
}
