package com.example.questionservice.dao;


import com.example.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    List<Question> findByDifficultylevelIgnoreCase(String difficulty);
    @Query(value = "SELECT q.id FROM question q WHERE q.difficultylevel=:difficulty ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
    List<Integer> findRandomQuestionsByDifficultylevelIgnoreCase(String difficulty, int numQ);
}
