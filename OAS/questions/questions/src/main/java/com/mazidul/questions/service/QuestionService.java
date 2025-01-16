package com.mazidul.questions.service;

import com.mazidul.questions.dao.QuestionDao;
import com.mazidul.questions.model.Question;
import com.mazidul.questions.model.QuestionWrapper;
import com.mazidul.questions.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionDao.findAll();
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        }
        return ResponseEntity.ok(questions);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        if (category == null || category.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(null);
        }

        List<Question> questions = questionDao.findByCategory(category);
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        }
        return ResponseEntity.ok(questions);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        if (question == null || question.getQuestionTitle() == null || question.getQuestionTitle().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Invalid question data");
        }

        questionDao.save(question);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Question added successfully");
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        Optional<Question> questionOptional = questionDao.findById(id);
        if (questionOptional.isPresent()) {
            questionDao.deleteById(id);
            return ResponseEntity.ok("Question deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Question not found with id: " + id);
        }
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        if (categoryName == null || categoryName.isEmpty() || numQuestions == null || numQuestions <= 0) {
            return ResponseEntity.badRequest()
                    .body(null);
        }

        List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName, numQuestions);
        if (questions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        }

        return ResponseEntity.ok(questions);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        if (questionIds == null || questionIds.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(null);
        }

        List<QuestionWrapper> wrappers = new ArrayList<>();
        for (Integer id : questionIds) {
            Optional<Question> questionOptional = questionDao.findById(id);
            if (questionOptional.isPresent()) {
                Question question = questionOptional.get();
                QuestionWrapper wrapper = new QuestionWrapper(
                        question.getId(),
                        question.getQuestionTitle(),
                        question.getOption1(),
                        question.getOption2(),
                        question.getOption3(),
                        question.getOption4());
                wrappers.add(wrapper);
            }
        }

        if (wrappers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        }

        return ResponseEntity.ok(wrappers);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        if (responses == null || responses.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(0);
        }

        int right = 0;
        for (Response response : responses) {
            Optional<Question> questionOptional = questionDao.findById(response.getId());
            if (questionOptional.isPresent()) {
                Question question = questionOptional.get();
                if (response.getResponse().equals(question.getRightAnswer())) {
                    right++;
                }
            }
        }

        return ResponseEntity.ok(right);
    }
}
