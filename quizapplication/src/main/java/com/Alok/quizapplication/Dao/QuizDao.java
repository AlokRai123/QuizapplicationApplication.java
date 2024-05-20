package com.Alok.quizapplication.Dao;

import com.Alok.quizapplication.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz,Integer> {
}
