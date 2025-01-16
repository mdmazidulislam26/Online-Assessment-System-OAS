package com.mazidul.questions.controller;

import com.mazidul.questions.model.Question;
import com.mazidul.questions.model.QuestionWrapper;
import com.mazidul.questions.service.QuestionService;
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

@WebMvcTest(QuestionController.class) // Specifies that this test is for the QuestionController
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc is used for simulating HTTP requests

    @MockBean
    private QuestionService questionService; // Mock the QuestionService

    // Test for getAllQuestion endpoint
    @Test
    public void testGetAllQuestion() throws Exception {
        // Mocking service response
        List<Question> mockQuestions = Arrays.asList(
                new Question(1, "What is Java?", "Language", "Coffee", "Game", "Platform", "Language", "Easy","Genaral"),
                new Question(2, "What is 2+2?", "3", "4", "5", "6", "4", "Easy","Genaral")
        );
        Mockito.when(questionService.getAllQuestions()).thenReturn(ResponseEntity.ok(mockQuestions));

        // Simulating a GET request
        mockMvc.perform(get("/questions/allQuestions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].questionTitle").value("What is Java?"));
    }

    // Test for getQuestionsByCategory endpoint
    @Test
    public void testGetQuestionsByCategory() throws Exception {
        // Mocking service response
        List<Question> mockQuestions = Arrays.asList(
                new Question(1, "What is Java?", "Language", "Coffee", "Game", "Platform", "Language", "Easy","Genaral")
        );
        Mockito.when(questionService.getQuestionsByCategory(eq("Technology"))).thenReturn(ResponseEntity.ok(mockQuestions));

        // Simulating a GET request
        mockMvc.perform(get("/questions/findByCategory/Technology"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    // Test for addQuestion endpoint
    @Test
    public void testAddQuestion() throws Exception {
        // Mocking service response
        Mockito.when(questionService.addQuestion(any())).thenReturn(ResponseEntity.ok("Question added successfully"));

        // Sample request body
        String requestBody = """
            {
                "id": 1,
                "questionTitle": "What is Java?",
                "option1": "Language",
                "option2": "Coffee",
                "option3": "Game",
                "option4": "Platform",
                "rightAnswer": "Language"
            }
        """;

        // Simulating a POST request
        mockMvc.perform(post("/questions/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Question added successfully"));
    }

    // Test for deleteQuestion endpoint
    @Test
    public void testDeleteQuestion() throws Exception {
        // Mocking service response
        Mockito.when(questionService.deleteQuestion(eq(1))).thenReturn(ResponseEntity.ok("Question deleted successfully"));

        // Simulating a DELETE request
        mockMvc.perform(delete("/questions/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Question deleted successfully"));
    }

    // Test for getQuestionsForQuiz endpoint
    @Test
    public void testGetQuestionsForQuiz() throws Exception {
        // Mocking service response
        List<Integer> questionIds = Arrays.asList(1, 2, 3);
        Mockito.when(questionService.getQuestionsForQuiz(eq("Science"), eq(3))).thenReturn(ResponseEntity.ok(questionIds));

        // Simulating a GET request
        mockMvc.perform(get("/questions/generate")
                        .param("categoryName", "Science")
                        .param("numQuestions", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0]").value(1));
    }

    // Test for getQuestionsFromId endpoint
    @Test
    public void testGetQuestionsFromId() throws Exception {
        // Mocking service response
        List<QuestionWrapper> mockQuestionWrappers = Arrays.asList(
                new QuestionWrapper(1, "What is Java?", "Language", "Coffee", "Game", "Platform")
        );
        Mockito.when(questionService.getQuestionsFromId(any())).thenReturn(ResponseEntity.ok(mockQuestionWrappers));

        // Sample request body
        String requestBody = "[1, 2, 3]";

        // Simulating a POST request
        mockMvc.perform(post("/questions/getQuestions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    // Test for getScore endpoint
    @Test
    public void testGetScore() throws Exception {
        // Mocking service response
        Mockito.when(questionService.getScore(any())).thenReturn(ResponseEntity.ok(3));

        // Sample request body
        String requestBody = """
            [
                {"id": 1, "response": "Language"},
                {"id": 2, "response": "4"}
            ]
        """;

        // Simulating a POST request
        mockMvc.perform(post("/questions/getScore")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }
}