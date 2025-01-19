package com.mazidul.questions.dao;

import com.mazidul.questions.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository  // Marks this interface as a Spring Data Repository.
public interface QuestionDao extends JpaRepository<Question, Integer> {

    // Method to find questions by their category.
    List<Question> findByCategory(String category);

    /**
     * Custom query to fetch random questions by category.
     * This query uses native SQL to randomly select a limited number of questions.
     *
     * @param category The category of questions to filter by.
     * @param numQ     The number of random questions to fetch.
     * @return A list of question IDs.
     */
    @Query(
            value = "SELECT q.id FROM question q WHERE q.category=:category ORDER BY RAND() LIMIT :numQ",
            nativeQuery = true
    )
    List<Integer> findRandomQuestionsByCategory(String category, int numQ);
}
