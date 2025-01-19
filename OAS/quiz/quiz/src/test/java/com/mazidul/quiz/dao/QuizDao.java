package com.mazidul.quiz.dao;

import com.mazidul.quiz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marks this interface as a repository for data access
public interface QuizDao extends JpaRepository<Quiz, Integer> {
    // JpaRepository provides CRUD operations for the Quiz entity (using Integer as the ID type)
}
